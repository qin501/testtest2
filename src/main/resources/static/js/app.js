window.app = {
	
	// netty服务后端发布的url地址
	//nettyServerUrl: 'ws://148.70.20.2:8088/ws',
    nettyServerUrl: 'ws://localhost:8088/ws',
	// 后端服务发布的url地址
	//serverUrl: 'http://148.70.20.2:8081',
    serverUrl: 'http://localhost:8081',
	// 图片服务器的url地址
    imgServerUrl: '',
	// 后端服务发布的url地址
	// 判断字符串是否为空
	isNotNull: function(str) {
		if (str != null && str != "" && str != undefined) {
			return true;
		}
		return false;
	},
		
	 // 保存用户的全局对象
	setUserGlobalInfo: function(user) {
		//第一次开始缓存，先清理
		localStorage.clear();
		
		var userInfoStr = JSON.stringify(user);
		localStorage.setItem("userInfo", userInfoStr);
	},
	
	 // 获取用户的全局对象
	getUserGlobalInfo: function() {
		var userInfoStr = localStorage.getItem("userInfo");
		return JSON.parse(userInfoStr);
	},
	
	
	// 登出后，移除用户全局对象	
	userLogout: function() {

		localStorage.clear();
		
		location.href = "login.html";
		console.log("okeeeeeeok");
	},
	
	
	// 保存用户的联系人列表	
	setContactList: function(contactList) {
		var contactListStr = JSON.stringify(contactList);
		localStorage.setItem("contactList", contactListStr);
	},
	
	
	// 获取本地缓存中的联系人列表	
	getContactList: function() {
		var contactListStr = localStorage.getItem("contactList");
		
		if (!this.isNotNull(contactListStr)) {
			return [];
		}
		
		return JSON.parse(contactListStr);
	},
	
			
	// 保存好友请求	
	setFriendRequests: function(FriendRequests) {
		var contactListStr = JSON.stringify(FriendRequests);
		localStorage.setItem("FriendRequests", contactListStr);
	},
	
	
	// 获取好友请求	
	getFriendRequests: function() {
		var contactListStr = localStorage.getItem("FriendRequests");
		
		if (!this.isNotNull(contactListStr)) {
			return [];
		}
		
		return JSON.parse(contactListStr);
	},
	
	
	
	// 根据用户id，从本地的缓存（联系人列表）中获取朋友的信息	
	getFriendFromContactList: function(friendId) {
		var contactListStr = localStorage.getItem("contactList");
		
		// 判断contactListStr是否为空
		if (this.isNotNull(contactListStr)) {
			// 不为空，则把用户信息返回
			var contactList = JSON.parse(contactListStr);
			for (var i = 0 ; i < contactList.length ; i ++) {
				var friend = contactList[i];
				if (friend.friendUserId == friendId) {
					return friend;
					break;
				}
			}
		} else {
			// 如果为空，直接返回null
			return null;
		}
	},
	
	
	// 保存用户的聊天记录
	// @param flag	判断本条消息是我发送的，还是朋友发送的，1:我  2:朋友	
	saveUserChatHistory: function(myId, friendId, msg, flag) {
		var me = this;
		var chatKey = "chat-" + myId + "-" + friendId;
		
		// 从本地缓存获取聊天记录是否存在
		var chatHistoryListStr = localStorage.getItem(chatKey);
		var chatHistoryList;
		if (me.isNotNull(chatHistoryListStr)) {
			// 如果不为空
			chatHistoryList = JSON.parse(chatHistoryListStr);
		} else {
			// 如果为空，赋一个空的list
			chatHistoryList = [];
		}
		
		// 构建聊天记录对象
		var singleMsg = new me.ChatHistory(myId, friendId, msg, flag);
		
		// 向list中追加msg对象
		chatHistoryList.push(singleMsg);
		
		localStorage.setItem(chatKey, JSON.stringify(chatHistoryList));
	},
	
	
	// 获取用户聊天记录	
	getUserChatHistory: function(myId, friendId) {
		var me = this;
		var chatKey = "chat-" + myId + "-" + friendId;
		var chatHistoryListStr = localStorage.getItem(chatKey);
		var chatHistoryList;
		if (me.isNotNull(chatHistoryListStr)) {
			// 如果不为空
			chatHistoryList = JSON.parse(chatHistoryListStr);
		} else {
			// 如果为空，赋一个空的list
			chatHistoryList = [];
		}
		
		return chatHistoryList;
	},
	
		
	// 删除我和朋友的聊天记录
	deleteUserChatHistory: function(myId, friendId) {
		var chatKey = "chat-" + myId + "-" + friendId;
		localStorage.removeItem(chatKey);
	},
	
	// 聊天记录的快照，仅仅保存每次和朋友聊天的最后一条消息	
	saveUserChatSnapshot: function(myId, friendId, msg, isRead) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;
		
		// 从本地缓存获取聊天快照的list
		var chatSnapshotListStr = localStorage.getItem(chatKey);
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			// 如果不为空
			chatSnapshotList = JSON.parse(chatSnapshotListStr);
			// 循环快照list，并且判断每个元素是否包含（匹配）friendId，如果匹配，则删除
			for (var i = 0 ; i < chatSnapshotList.length ; i ++) {
				if (chatSnapshotList[i].friendId == friendId) {
					// 删除已经存在的friendId所对应的快照对象
					chatSnapshotList.splice(i, 1);
					break;
				}
			}
		} else {
			// 如果为空，赋一个空的list
			chatSnapshotList = [];
		}
		
		// 构建聊天快照对象
		var singleMsg = new me.ChatSnapshot(myId, friendId, msg, isRead);
		
		// 向list中追加快照对象
		chatSnapshotList.unshift(singleMsg);
		
		localStorage.setItem(chatKey, JSON.stringify(chatSnapshotList));
	},
	
	
	// 获取用户快照记录列表	
	getUserChatSnapshot: function(myId) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;
		// 从本地缓存获取聊天快照的list
		var chatSnapshotListStr = localStorage.getItem(chatKey);
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			// 如果不为空
			chatSnapshotList = JSON.parse(chatSnapshotListStr);
		} else {
			// 如果为空，赋一个空的list
			chatSnapshotList = [];
		}
		
		return chatSnapshotList;
	},
	
	
	// 删除本地的聊天快照记录	
	deleteUserChatSnapshot: function(myId, friendId) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;
		
		// 从本地缓存获取聊天快照的list
		var chatSnapshotListStr = localStorage.getItem(chatKey);
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			// 如果不为空
			chatSnapshotList = JSON.parse(chatSnapshotListStr);
			// 循环快照list，并且判断每个元素是否包含（匹配）friendId，如果匹配，则删除
			for (var i = 0 ; i < chatSnapshotList.length ; i ++) {
				if (chatSnapshotList[i].friendId == friendId) {
					// 删除已经存在的friendId所对应的快照对象
					chatSnapshotList.splice(i, 1);
					break;
				}
			}
		} else {
			// 如果为空，不做处理
			return;
		}
		
		localStorage.setItem(chatKey, JSON.stringify(chatSnapshotList));
	},
	
	// 标记未读消息为已读状态	
	readUserChatSnapshot: function(myId, friendId) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;
		// 从本地缓存获取聊天快照的list
		var chatSnapshotListStr = localStorage.getItem(chatKey);
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			// 如果不为空
			chatSnapshotList = JSON.parse(chatSnapshotListStr);
			// 循环这个list，判断是否存在好友，比对friendId，
			// 如果有，在list中的原有位置删除该 快照 对象，然后重新放入一个标记已读的快照对象
			for (var i = 0 ; i < chatSnapshotList.length ; i ++) {
				var item = chatSnapshotList[i];
				if (item.friendId == friendId) {
					item.isRead = true;		// 标记为已读
					chatSnapshotList.splice(i, 1, item);	// 替换原有的快照
					break;
				}
			}
			// 替换原有的快照列表
			localStorage.setItem(chatKey, JSON.stringify(chatSnapshotList));
		} else {
			// 如果为空
			return;
		}
	},

	
	// 和后端的枚举对应	
	CONNECT: 1, 	// 第一次(或重连)初始化连接
	CHAT: 2, 		// 聊天消息
	SIGNED: 3, 		// 消息签收
	KEEPALIVE: 4, 	// 客户端保持心跳
	PULL_FRIEND:5,	// 重新拉取好友
	ISLOGIN:6,	// 重新拉取好友
	
	
	// 和后端的 ChatMsg 聊天模型对象保持一致	
	ChatMsg: function(senderId, receiverId, msg, msgId){
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.msg = msg;
		this.msgId = msgId;
	},
	
	
	// 构建消息 DataContent 模型对象	
	DataContent: function(action, chatMsg, extand){
		this.action = action;
		this.chatMsg = chatMsg;
		this.extand = extand;
	},
	
	
	// 单个聊天记录的对象	
	ChatHistory: function(myId, friendId, msg, flag){
		this.myId = myId;
		this.friendId = friendId;
		this.msg = msg;
		this.flag = flag;
	},
	
	
	// 快照对象
	//	isRead	用于判断消息是否已读还是未读	
	ChatSnapshot: function(myId, friendId, msg, isRead){
		this.myId = myId;
		this.friendId = friendId;
		this.msg = msg;
		this.isRead = isRead;
	}
	
}
