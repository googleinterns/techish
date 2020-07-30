type authInfo = {
    loginUrl: string;
    logoutUrl: string;
    hasProfile: boolean;
};

function setForm(value: string) {
    const menteeForm = document.getElementById('Mentee');
    const mentorForm = document.getElementById('Mentor');
    const userTypeButton = document.getElementById('user-input');

    const menteeFormInputs = document.querySelectorAll("[id='mentee']");

    const mentorFormInputs = document.querySelectorAll("[id='mentor']");

    if (menteeForm && mentorForm && userTypeButton) {
        if (value == 'Mentee') {
            menteeForm.setAttribute('style', 'display:block;');
            mentorForm.setAttribute('style', 'display:none;');
            userTypeButton.setAttribute('value', 'Mentee');

            if (menteeFormInputs && mentorFormInputs) {
                setRequiredInputs(menteeFormInputs, true);
                setRequiredInputs(mentorFormInputs, false);
            }
            else {
                console.error("Required forms failed for mentee form");
            }
        }
        else {
            mentorForm.setAttribute('style', 'display:block;');
            menteeForm.setAttribute('style', 'display:none;');
            userTypeButton.setAttribute('value', 'Mentor');

            if (menteeFormInputs && mentorFormInputs) {
                setRequiredInputs(mentorFormInputs, true);
                setRequiredInputs(menteeFormInputs, false);
            }
            else {
                console.error("Required forms failed for mentor form");
            }
        }
    }
}

function setRequiredInputs(elements: any, boolValue: boolean) {
    for(let i= 0; i < elements.length; i++){
        elements[i].required = boolValue;
    }

}

async function redirectIfLoggedOut() {
    const logStatus = await logStatusGetter();
    if (logStatus.loginUrl != "") {
        document.location.href = logStatus.loginUrl;
        alert("Please login or create an account.");
    }
}
async function logStatusGetter(): Promise<authInfo> {
    const response = await fetch('/userapi');
    const currentStatus = await response.json();
    let authStatus: authInfo = { loginUrl: currentStatus.LogInUrl, logoutUrl: currentStatus.LogOutUrl, hasProfile: currentStatus.HasProfile };
    return authStatus;
}

window.onload = () => {
    redirectIfLoggedOut();
}
