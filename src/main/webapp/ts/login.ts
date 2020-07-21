
type authInfoFromServlet = {
    loginUrl: string;
    logoutUrl: string;
    hasProfile: boolean;
};
//function that loads the home page for logging in and logging out
async function loadHome() {
    const logging = document.getElementById('navbarResponsive');
    const link = document.getElementById('login-link');
    const signUpLink = document.getElementById('signup-link');
   
    const logStatus = await logStatusMethod();

    if(logStatus.hasProfile == false && logStatus.loginUrl == ""){
        document.location.href = "/profileBuilder.html";
    }

    //set up function to set login/logout link based on which string is non empty
    if (link && logging && signUpLink) {
        if (logStatus.loginUrl === "") {
            link.setAttribute('href', logStatus.logoutUrl);
            link.innerHTML = 'Logout';
            signUpLink.setAttribute('style','display:none;');

        } else {
            link.setAttribute('href', logStatus.loginUrl);
            link.innerHTML = 'Login';
        }
    }
}

async function logStatusMethod(): Promise<authInfoFromServlet> {
    const response = await fetch('/userapi');
    const currentStatus = await response.json();
    let authStatus: authInfoFromServlet = { loginUrl: currentStatus.LogInUrl, logoutUrl: currentStatus.LogOutUrl, hasProfile: currentStatus.HasProfile };
    return authStatus;
}

window.onload = () => {
    loadHome();
}