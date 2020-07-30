type authInfo = {
    loginUrl: string;
    logoutUrl: string;
    hasProfile: boolean;
};

function setForm(value: string) {
    const menteeForm = document.getElementById('Mentee');
    const mentorForm = document.getElementById('Mentor');
    const userTypeButton = document.getElementById('user-input');

    const menteeInputs:string[] = ['name-input','school-input', 'major-input' ];
    const mentorInputs:string[] =['profName-input','company-input','careerTitle-input', 'bio-input', 'specialty-input']

    if (menteeForm && mentorForm && userTypeButton) {
        if (value == 'Mentee') {
            menteeForm.setAttribute('style', 'display:block;');
            mentorForm.setAttribute('style', 'display:none;');
            userTypeButton.setAttribute('value', 'Mentee');

            if (menteeInputs && mentorInputs) {
                setRequiredInputs(menteeInputs, true);
                setRequiredInputs(mentorInputs, false);
            }
            else {
                console.error("Required forms failed for mentee form");
            }
        }
        else {
            mentorForm.setAttribute('style', 'display:block;');
            menteeForm.setAttribute('style', 'display:none;');
            userTypeButton.setAttribute('value', 'Mentor');

            if (mentorInputs && menteeInputs) {
                setRequiredInputs(mentorInputs, true);
                setRequiredInputs(menteeInputs, false);
            }
            else {
                console.error("Required forms failed for mentor form");
            }
        }
    }
}

function setRequiredInputs(elements: any, boolValue: boolean) {
    for(let i= 0; i < elements.length; i++){
        const currentElement = document.getElementById(elements[i]) as HTMLInputElement;
        currentElement.required = boolValue;
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
