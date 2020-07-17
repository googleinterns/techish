
type authInfo = {
    loginUrl: string;
    logoutUrl: string;
    hasProfile: boolean;
};
//function that loads the home page for logging in and logging out
async function loadHome() {
    const logging = document.getElementById('navbarResponsive');
    const link = document.getElementById('login-link');
   
    const logStatus = await logStatusMethod();
    if(logStatus.hasProfile == false && logStatus.loginUrl == ""){
        console.log(1);
        document.location.href = "/profileBuilder.html";
    }
    //set up function to set login/logout link based on which string is non empty
    if (link && logging) {

        if (logStatus.loginUrl === "") {
            link.setAttribute('href', logStatus.logoutUrl);
            link.innerHTML = 'Logout';
        } else {
            link.setAttribute('href', logStatus.loginUrl);
            link.innerHTML = 'Login';
        }
    }
}

async function logStatusMethod(): Promise<authInfo> {
    const response = await fetch('/userapi');
    const currentStatus = await response.json();
    let authStatus: authInfo = { loginUrl: currentStatus.LogInUrl, logoutUrl: currentStatus.LogOutUrl, hasProfile: currentStatus.HasProfile };
    return authStatus;
}

window.onload = () => {
    loadHome();
}