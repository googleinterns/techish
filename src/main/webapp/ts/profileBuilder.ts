type authInfoFromServlet = {
    loginUrl: string;
    logoutUrl: string;
    hasProfile: boolean;
};

function setForm(value: string) {
    const menteeForm =  document.getElementById('Mentee');
    const mentorForm = document.getElementById('Mentor');
    const userTypeButton = document.getElementById('user-input');

    if(menteeForm && mentorForm && userTypeButton){
        if(value == 'Mentee') {
            menteeForm.setAttribute('style','display:block;');
            mentorForm.setAttribute('style','display:none;');
            userTypeButton.setAttribute('value','Mentee');
        }
        else {
            mentorForm.setAttribute('style','display:block;');
            menteeForm.setAttribute('style','display:none;');
            userTypeButton.setAttribute('value','Mentor');
        }
    }
} 

async function redirectIfLoggedOut() {
    const logStatus = await logStatusMethod();
    if(logStatus.loginUrl != "") {
        document.location.href = logStatus.loginUrl;
        alert("Please login or create an account.");
    }
}
async function logStatusMethod(): Promise<authInfoFromServlet> {
    const response = await fetch('/userapi');
    const currentStatus = await response.json();
    let authStatus: authInfoFromServlet = { loginUrl: currentStatus.LogInUrl, logoutUrl: currentStatus.LogOutUrl, hasProfile: currentStatus.HasProfile };
    return authStatus;
}

window.onload = () => {
    redirectIfLoggedOut();
}