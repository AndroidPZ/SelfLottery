package zhcw.lottery.znzd.com.selflottery.ui.my.entity;

/**
 * 作者：XPZ on 2018/4/27 15:51.
 */
public class User_Info {


    /**
     * return_code : SUCCESS
     * return_msg : 成功
     * socket_ip : 120.92.34.155
     * socket_port : 8848
     * user : {"email":"","headurl":"","isAuth":1,"isOwner":0,"isReal":1,"nickname":"131****1111","password":"","phone":"13111111111","status":0,"username":"13111111111"}
     */

    private String return_code;
    private String return_msg;
    private String socket_ip;
    private int socket_port;
    private UserBean user;

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

    public String getSocket_ip() {
        return socket_ip == null ? "" : socket_ip;
    }

    public void setSocket_ip(String socket_ip) {
        this.socket_ip = socket_ip;
    }

    public int getSocket_port() {
        return socket_port;
    }

    public void setSocket_port(int socket_port) {
        this.socket_port = socket_port;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * email :
         * headurl :
         * isAuth : 1
         * isOwner : 0
         * isReal : 1
         * nickname : 131****1111
         * password :
         * phone : 13111111111
         * status : 0
         * username : 13111111111
         */

        private String email;
        private String headurl;
        private String nickname;
        private String password;
        private String phone;
        private String username;
        private int isAuth;
        private int isOwner;
        private int isReal;
        private int status;

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

        public String getNickname() {
            return nickname == null ? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password == null ? "" : password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone == null ? "" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUsername() {
            return username == null ? "" : username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getIsAuth() {
            return isAuth;
        }

        public void setIsAuth(int isAuth) {
            this.isAuth = isAuth;
        }

        public int getIsOwner() {
            return isOwner;
        }

        public void setIsOwner(int isOwner) {
            this.isOwner = isOwner;
        }

        public int getIsReal() {
            return isReal;
        }

        public void setIsReal(int isReal) {
            this.isReal = isReal;
        }


        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }
}
