package com.xhh.ticketver2.beans;

import java.util.List;

public class MyTeamListBean implements MultiItemEntity {

    public CommEntry commEntry;
    /**
     * status : true
     * obj : {"totalNum":2,"rows":[{"userId":"101208","parentId":"1001","parentUserName":"test","parentIdList":"1000,1001","userType":1,"userProp":1,"nickName":"","headPortrait":"/content/lotview/images/user/u1.png","relName":"","userName":"阿飞","pwd":"3aef6ae503f0ad5acd96662806138814","identityCard":"","phone":"","qq":"123456","email":"","rebate":1800,"availableFund":"7984.00","frostFund":"0","totalBonus":"3930.00","totalScore":0,"totalBetTask":"11000.00","betTask":"6536.00","platform":1,"registerIp":"36.157.4.247","registerAddress":"","registerType":2,"lastLoginIp":"36.157.25.239","lastLoginAddress":"","lastLoginTime":"2020-08-22 11:44:01","loginNum":28,"isGroupChatAdmin":0,"groupChatStatus":1,"status":1,"createTime":"2020-08-12 18:00:53","lastOperatePersonId":"3486685744920343345","lastOperateTime":"2020-08-22 11:44:01","lastChangeTime":"2020-08-22 11:44:01","platformStr":"PC","userTypeStr":"代理","userPropStr":"会员","createTimeStr":"2020-08-12 18:00:53","lastLoginTimeStr":"2020-08-22 11:44:01","statusHtml":"<span class=\"label label-success\">正常<\/span>"},{"userId":"1003","parentId":"1001","parentUserName":"test","parentIdList":"1001","userType":1,"userProp":1,"nickName":"users","headPortrait":"/content/lotview/images/user/u1.png","relName":"users","userName":"users","pwd":"22caaccb1df024d76bcdea983c87c2db","safetyPassword":"2c726fa4ad66bc89c3b28e1d90d19df5","identityCard":"","phone":"","qq":"234234566","email":"","rebate":1800,"availableFund":"2643.99","frostFund":"0","totalBonus":"3899.99","totalScore":0,"totalBetTask":"1100.00","betTask":"0","platform":1,"registerIp":"125.84.179.202","registerAddress":"重庆市 电信","registerType":2,"lastLoginIp":"36.157.4.247","lastLoginAddress":"","lastLoginTime":"2020-08-12 17:56:19","loginNum":10,"isGroupChatAdmin":0,"groupChatStatus":1,"status":1,"createTime":"2017-11-14 16:03:19","lastOperatePersonId":"6f675bf223804f1eb9189953fdb0d505","lastOperateTime":"2020-08-12 17:56:19","lastChangeTime":"2020-08-12 17:56:19","platformStr":"PC","userTypeStr":"代理","userPropStr":"会员","createTimeStr":"2017-11-14 16:03:19","lastLoginTimeStr":"2020-08-12 17:56:19","statusHtml":"<span class=\"label label-success\">正常<\/span>"}]}
     */

    private boolean status;
    private ObjBean obj;

    @Override
    public int getItemType() {
        return 0;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * totalNum : 2
         * rows : [{"userId":"101208","parentId":"1001","parentUserName":"test","parentIdList":"1000,1001","userType":1,"userProp":1,"nickName":"","headPortrait":"/content/lotview/images/user/u1.png","relName":"","userName":"阿飞","pwd":"3aef6ae503f0ad5acd96662806138814","identityCard":"","phone":"","qq":"123456","email":"","rebate":1800,"availableFund":"7984.00","frostFund":"0","totalBonus":"3930.00","totalScore":0,"totalBetTask":"11000.00","betTask":"6536.00","platform":1,"registerIp":"36.157.4.247","registerAddress":"","registerType":2,"lastLoginIp":"36.157.25.239","lastLoginAddress":"","lastLoginTime":"2020-08-22 11:44:01","loginNum":28,"isGroupChatAdmin":0,"groupChatStatus":1,"status":1,"createTime":"2020-08-12 18:00:53","lastOperatePersonId":"3486685744920343345","lastOperateTime":"2020-08-22 11:44:01","lastChangeTime":"2020-08-22 11:44:01","platformStr":"PC","userTypeStr":"代理","userPropStr":"会员","createTimeStr":"2020-08-12 18:00:53","lastLoginTimeStr":"2020-08-22 11:44:01","statusHtml":"<span class=\"label label-success\">正常<\/span>"},{"userId":"1003","parentId":"1001","parentUserName":"test","parentIdList":"1001","userType":1,"userProp":1,"nickName":"users","headPortrait":"/content/lotview/images/user/u1.png","relName":"users","userName":"users","pwd":"22caaccb1df024d76bcdea983c87c2db","safetyPassword":"2c726fa4ad66bc89c3b28e1d90d19df5","identityCard":"","phone":"","qq":"234234566","email":"","rebate":1800,"availableFund":"2643.99","frostFund":"0","totalBonus":"3899.99","totalScore":0,"totalBetTask":"1100.00","betTask":"0","platform":1,"registerIp":"125.84.179.202","registerAddress":"重庆市 电信","registerType":2,"lastLoginIp":"36.157.4.247","lastLoginAddress":"","lastLoginTime":"2020-08-12 17:56:19","loginNum":10,"isGroupChatAdmin":0,"groupChatStatus":1,"status":1,"createTime":"2017-11-14 16:03:19","lastOperatePersonId":"6f675bf223804f1eb9189953fdb0d505","lastOperateTime":"2020-08-12 17:56:19","lastChangeTime":"2020-08-12 17:56:19","platformStr":"PC","userTypeStr":"代理","userPropStr":"会员","createTimeStr":"2017-11-14 16:03:19","lastLoginTimeStr":"2020-08-12 17:56:19","statusHtml":"<span class=\"label label-success\">正常<\/span>"}]
         */

        private int totalNum;
        private List<RowsBean> rows;

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * userId : 101208
             * parentId : 1001
             * parentUserName : test
             * parentIdList : 1000,1001
             * userType : 1
             * userProp : 1
             * nickName :
             * headPortrait : /content/lotview/images/user/u1.png
             * relName :
             * userName : 阿飞
             * pwd : 3aef6ae503f0ad5acd96662806138814
             * identityCard :
             * phone :
             * qq : 123456
             * email :
             * rebate : 1800
             * availableFund : 7984.00
             * frostFund : 0
             * totalBonus : 3930.00
             * totalScore : 0
             * totalBetTask : 11000.00
             * betTask : 6536.00
             * platform : 1
             * registerIp : 36.157.4.247
             * registerAddress :
             * registerType : 2
             * lastLoginIp : 36.157.25.239
             * lastLoginAddress :
             * lastLoginTime : 2020-08-22 11:44:01
             * loginNum : 28
             * isGroupChatAdmin : 0
             * groupChatStatus : 1
             * status : 1
             * createTime : 2020-08-12 18:00:53
             * lastOperatePersonId : 3486685744920343345
             * lastOperateTime : 2020-08-22 11:44:01
             * lastChangeTime : 2020-08-22 11:44:01
             * platformStr : PC
             * userTypeStr : 代理
             * userPropStr : 会员
             * createTimeStr : 2020-08-12 18:00:53
             * lastLoginTimeStr : 2020-08-22 11:44:01
             * statusHtml : <span class="label label-success">正常</span>
             * safetyPassword : 2c726fa4ad66bc89c3b28e1d90d19df5
             */

            private String userId;
            private String parentId;
            private String parentUserName;
            private String parentIdList;
            private int userType;
            private int userProp;
            private String nickName;
            private String headPortrait;
            private String relName;
            private String userName;
            private String pwd;
            private String identityCard;
            private String phone;
            private String qq;
            private String email;
            private int rebate;
            private String availableFund;
            private String frostFund;
            private String totalBonus;
            private int totalScore;
            private String totalBetTask;
            private String betTask;
            private int platform;
            private String registerIp;
            private String registerAddress;
            private int registerType;
            private String lastLoginIp;
            private String lastLoginAddress;
            private String lastLoginTime;
            private int loginNum;
            private int isGroupChatAdmin;
            private int groupChatStatus;
            private int status;
            private String createTime;
            private String lastOperatePersonId;
            private String lastOperateTime;
            private String lastChangeTime;
            private String platformStr;
            private String userTypeStr;
            private String userPropStr;
            private String createTimeStr;
            private String lastLoginTimeStr;
            private String statusHtml;
            private String safetyPassword;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getParentUserName() {
                return parentUserName;
            }

            public void setParentUserName(String parentUserName) {
                this.parentUserName = parentUserName;
            }

            public String getParentIdList() {
                return parentIdList;
            }

            public void setParentIdList(String parentIdList) {
                this.parentIdList = parentIdList;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public int getUserProp() {
                return userProp;
            }

            public void setUserProp(int userProp) {
                this.userProp = userProp;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getHeadPortrait() {
                return headPortrait;
            }

            public void setHeadPortrait(String headPortrait) {
                this.headPortrait = headPortrait;
            }

            public String getRelName() {
                return relName;
            }

            public void setRelName(String relName) {
                this.relName = relName;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getPwd() {
                return pwd;
            }

            public void setPwd(String pwd) {
                this.pwd = pwd;
            }

            public String getIdentityCard() {
                return identityCard;
            }

            public void setIdentityCard(String identityCard) {
                this.identityCard = identityCard;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public int getRebate() {
                return rebate;
            }

            public void setRebate(int rebate) {
                this.rebate = rebate;
            }

            public String getAvailableFund() {
                return availableFund;
            }

            public void setAvailableFund(String availableFund) {
                this.availableFund = availableFund;
            }

            public String getFrostFund() {
                return frostFund;
            }

            public void setFrostFund(String frostFund) {
                this.frostFund = frostFund;
            }

            public String getTotalBonus() {
                return totalBonus;
            }

            public void setTotalBonus(String totalBonus) {
                this.totalBonus = totalBonus;
            }

            public int getTotalScore() {
                return totalScore;
            }

            public void setTotalScore(int totalScore) {
                this.totalScore = totalScore;
            }

            public String getTotalBetTask() {
                return totalBetTask;
            }

            public void setTotalBetTask(String totalBetTask) {
                this.totalBetTask = totalBetTask;
            }

            public String getBetTask() {
                return betTask;
            }

            public void setBetTask(String betTask) {
                this.betTask = betTask;
            }

            public int getPlatform() {
                return platform;
            }

            public void setPlatform(int platform) {
                this.platform = platform;
            }

            public String getRegisterIp() {
                return registerIp;
            }

            public void setRegisterIp(String registerIp) {
                this.registerIp = registerIp;
            }

            public String getRegisterAddress() {
                return registerAddress;
            }

            public void setRegisterAddress(String registerAddress) {
                this.registerAddress = registerAddress;
            }

            public int getRegisterType() {
                return registerType;
            }

            public void setRegisterType(int registerType) {
                this.registerType = registerType;
            }

            public String getLastLoginIp() {
                return lastLoginIp;
            }

            public void setLastLoginIp(String lastLoginIp) {
                this.lastLoginIp = lastLoginIp;
            }

            public String getLastLoginAddress() {
                return lastLoginAddress;
            }

            public void setLastLoginAddress(String lastLoginAddress) {
                this.lastLoginAddress = lastLoginAddress;
            }

            public String getLastLoginTime() {
                return lastLoginTime;
            }

            public void setLastLoginTime(String lastLoginTime) {
                this.lastLoginTime = lastLoginTime;
            }

            public int getLoginNum() {
                return loginNum;
            }

            public void setLoginNum(int loginNum) {
                this.loginNum = loginNum;
            }

            public int getIsGroupChatAdmin() {
                return isGroupChatAdmin;
            }

            public void setIsGroupChatAdmin(int isGroupChatAdmin) {
                this.isGroupChatAdmin = isGroupChatAdmin;
            }

            public int getGroupChatStatus() {
                return groupChatStatus;
            }

            public void setGroupChatStatus(int groupChatStatus) {
                this.groupChatStatus = groupChatStatus;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getLastOperatePersonId() {
                return lastOperatePersonId;
            }

            public void setLastOperatePersonId(String lastOperatePersonId) {
                this.lastOperatePersonId = lastOperatePersonId;
            }

            public String getLastOperateTime() {
                return lastOperateTime;
            }

            public void setLastOperateTime(String lastOperateTime) {
                this.lastOperateTime = lastOperateTime;
            }

            public String getLastChangeTime() {
                return lastChangeTime;
            }

            public void setLastChangeTime(String lastChangeTime) {
                this.lastChangeTime = lastChangeTime;
            }

            public String getPlatformStr() {
                return platformStr;
            }

            public void setPlatformStr(String platformStr) {
                this.platformStr = platformStr;
            }

            public String getUserTypeStr() {
                return userTypeStr;
            }

            public void setUserTypeStr(String userTypeStr) {
                this.userTypeStr = userTypeStr;
            }

            public String getUserPropStr() {
                return userPropStr;
            }

            public void setUserPropStr(String userPropStr) {
                this.userPropStr = userPropStr;
            }

            public String getCreateTimeStr() {
                return createTimeStr;
            }

            public void setCreateTimeStr(String createTimeStr) {
                this.createTimeStr = createTimeStr;
            }

            public String getLastLoginTimeStr() {
                return lastLoginTimeStr;
            }

            public void setLastLoginTimeStr(String lastLoginTimeStr) {
                this.lastLoginTimeStr = lastLoginTimeStr;
            }

            public String getStatusHtml() {
                return statusHtml;
            }

            public void setStatusHtml(String statusHtml) {
                this.statusHtml = statusHtml;
            }

            public String getSafetyPassword() {
                return safetyPassword;
            }

            public void setSafetyPassword(String safetyPassword) {
                this.safetyPassword = safetyPassword;
            }
        }
    }
}
