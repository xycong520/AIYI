/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xiuman.xinjiankang.bean;


import com.hyphenate.chat.EMContact;

/**
 * 环信用户
 */
public class HXUser extends EMContact {
	//未读消息数
	private int unreadMsgCount;
	//头字母
	private String header;
	//头像
	private String avatar;
	//身份标识(1:普通用户，2：医生，3：客服)
	private int identity;
	//用户id
	private String userid;
	public HXUser(){}

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public HXUser(String username){
		super(username);
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public int getUnreadMsgCount() {
		return unreadMsgCount;
	}

	public void setUnreadMsgCount(int unreadMsgCount) {
		this.unreadMsgCount = unreadMsgCount;
	}



	public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
	public int hashCode() {
		return 17 * getUsername().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof HXUser)) {
			return false;
		}
		return getUsername().equals(((HXUser) o).getUsername());
	}

	@Override
	public String toString() {
		return nick == null ? username : nick;
	}
}
