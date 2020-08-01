type authInfo = {
    loginUrl: string;
    logoutUrl: string;
    hasProfile: boolean;
};

function setForm(value: string) {
    const userTypeButton = document.getElementById('user-input');
    const menteeInputs: string[] = ['name-input', 'school-input', 'major-input'];
    const mentorInputs: string[] = ['profName-input', 'company-input', 'careerTitle-input', 'bio-input', 'specialty-input']

    if (userTypeButton == null) {
        console.error("userTypeButton is null");
    }
    const visibleId = value;
    const hiddenId = visibleId == 'Mentee' ? 'Mentor' : 'Mentee';
    const hiddenInputs = hiddenId == 'Mentee' ? menteeInputs : mentorInputs;
    const visibleInputs = visibleId == 'Mentee' ? menteeInputs : mentorInputs;
 
    showForm(visibleId, userTypeButton, visibleInputs);
    hideForm(hiddenId, hiddenInputs); 
}

function showForm(visibleId: string, userTypeButton: HTMLElement, requiredInputs: string[]) {
    document.getElementById(visibleId)!.setAttribute('style', 'display:block;');
    userTypeButton.setAttribute('value', visibleId);
    setRequiredInputs(requiredInputs, true);
}

function hideForm(hiddenId: string, requiredInputs: string[]) {
    document.getElementById(hiddenId)!.setAttribute('style', 'display:none;');
    setRequiredInputs(requiredInputs, false);
}
function setRequiredInputs(arrayofFormIDs: string[], isIdRequired: boolean) {
    for (let i = 0; i < arrayofFormIDs.length; i++) {
        const currentElement = document.getElementById(arrayofFormIDs[i]) as HTMLInputElement;
        if (currentElement == null) {
            console.error("Element doesn't exist");
        }
        else {
            currentElement.required = isIdRequired;
        }
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
