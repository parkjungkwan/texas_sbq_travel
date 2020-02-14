package texas.sbq.travel.mapper;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import texas.sbq.travel.user.User;

@Repository
public interface UserMapper {
	public void insertUser(User user);
	public User selectByIdPw(User user);
	public int existId(String uid);
	public void createUser(HashMap<String, String>paramMap);
	public void dropUser(HashMap<String, String>paramMap);
	public void removeUser(User user);
	public void truncateUser(HashMap<String,String> paramMap);
	
	public User selectFindId(User user);
	public User selectfindPwd(User user);
}