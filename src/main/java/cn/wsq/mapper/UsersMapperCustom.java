package cn.wsq.mapper;



import cn.wsq.pojo.Users;
import cn.wsq.pojo.vo.FriendRequestVO;
import cn.wsq.pojo.vo.MyFriendsVO;
import cn.wsq.utils.MyMapper;

import java.util.List;

public interface UsersMapperCustom extends MyMapper<Users> {
	
	public List<FriendRequestVO> queryFriendRequestList(String acceptUserId);
	
	public List<MyFriendsVO> queryMyFriends(String userId);
	
	public void batchUpdateMsgSigned(List<String> msgIdList);
	
}