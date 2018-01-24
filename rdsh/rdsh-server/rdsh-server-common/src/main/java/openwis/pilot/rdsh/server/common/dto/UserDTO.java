package openwis.pilot.rdsh.server.common.dto;

import java.io.Serializable;


public class UserDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;

  private String password;

  public UserDTO() {

  }

  public UserDTO(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  @Override
  public String toString() {
    return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
  }

}

