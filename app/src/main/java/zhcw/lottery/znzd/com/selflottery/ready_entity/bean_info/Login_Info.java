package zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info;

import java.io.Serializable;

/**
 * 作者：XPZ on 2018/8/7 09:25.
 */
public class Login_Info implements Serializable {
    /**
     * return_code : SUCCESS
     * return_msg : 成功
     * user : {"username":"13111111111","password":"","nickname":"131****1111","phone":"13111111111","email":"","headurl":"","status":0,"isOwner":0}
     * token : 1fb74ac97e2f4ea4a5783c80baa106c1
     * socket_ip : 120.92.34.155
     * socket_port : 8848
     */
    private String return_code;
    private String return_msg;
    private UserBean user;
    private String token;
    private String socket_ip;
    private int socket_port;

    public int getSocket_port() {
        return socket_port;
    }

    public void setSocket_port(int socket_port) {
        this.socket_port = socket_port;
    }

    public String getReturn_code() {
        return return_code == null ? "" : return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg == null ? "" : return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSocket_ip() {
        return socket_ip == null ? "" : socket_ip;
    }

    public void setSocket_ip(String socket_ip) {
        this.socket_ip = socket_ip;
    }

    public static class UserBean {
        /**
         * username : 13111111111
         * password :
         * nickname : 131****1111
         * phone : 13111111111
         * email :
         * headurl :
         * status : 0
         * isOwner : 0
         */

        private String username;
        private String password;
        private String nickname;
        private String phone;
        private String email;
        private String headurl;
        private int status;
        private int isOwner;

        public String getUsername() {
            return username == null ? "" : username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password == null ? "" : password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNickname() {
            return nickname == null ? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhone() {
            return phone == null ? "" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email == null ? "" : email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getHeadurl() {
            return headurl == null ? "" : headurl;
        }

        public void setHeadurl(String headurl) {
            this.headurl = headurl;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIsOwner() {
            return isOwner;
        }

        public void setIsOwner(int isOwner) {
            this.isOwner = isOwner;
        }
    }
}
