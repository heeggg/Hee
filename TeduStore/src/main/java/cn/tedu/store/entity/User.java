package cn.tedu.store.entity;

public class User extends BaseEntity {

	private static final long serialVersionUID = 6620940960359286896L;
	
	private Integer id;
	private String username;
	private String password;
	private String email;
	private String phone;
	private Integer gender;

	// 头像
	private String avatar;
	private String salt;
	// 状态
	private Integer status;
	// 是否删除
	private Integer isDelete;

	public User() {
		super();
	}

	public User(String username, String password, String email, String phone, Integer gender) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

}
