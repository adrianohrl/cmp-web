/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function handleDialogRequest(xhr, status, args) {
    if(args.validationFailed || !args.positiveExpectedDuration) {
        PF('dlg').jq.effect("shake", {times:5}, 100);
    } else {
        PF('dlg').hide();
    }
}