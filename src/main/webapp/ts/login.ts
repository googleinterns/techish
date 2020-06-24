
type authInfo = {
    loginUrl: string;
    logoutUrl: string;
};
//function that loads the home page for logging in and logging out
async function loadHome() {
    const logging = document.getElementById('login-status-section');
    const link = document.getElementById('login-link');

    const logStatus = await getLogStatus();

    //set up function to set login/logout link based on which string is non empty
    if (link && logging) {

        if (logStatus.loginUrl === "") {
            link.setAttribute('href', logStatus.logoutUrl);
            console.log(logStatus.logoutUrl);
            link.innerHTML = 'Logout';
        } else {
            link.setAttribute('href', logStatus.loginUrl);
            link.innerHTML = 'Login';
        }
    }
}

async function getLogStatus(): Promise<authInfo> {
    const response = await fetch('/userapi');
    const currentStatus = await response.json();
    let authStatus: authInfo = { loginUrl: currentStatus.LogInUrl, logoutUrl: currentStatus.LogOutUrl };
    return authStatus;
}

window.onload = () => {
    loadHome();
}