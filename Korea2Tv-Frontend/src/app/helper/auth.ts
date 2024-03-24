class Auth{
    isLoggedIn() {
        if(this.currentAccessToken() == null){
          return false;
        }else{
          return true;
        }
    }
    
    logout() {
      localStorage.removeItem('authUser');
      localStorage.clear();
    }

    currentAccessToken(){
        return localStorage.getItem('authUser');
    }
}

export const authUtils = new Auth();