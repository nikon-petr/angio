<p>Добрый день!</p>
<p>Учетная запись суперпользователя ${data.email} зарегистрирована, а пароль сброшен в целях безопасности.</p>
<p>Для разблокировки учетной записи необходимо восстановить пароль. Для этого нажмите на кнопку ниже</p>
<p>Код восстановления: <b>${data.password}</b></p>
<table role="presentation" border="0" cellpadding="0" cellspacing="0" class="btn btn-primary">
    <tbody>
    <tr>
        <td align="left">
            <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                <tbody>
                <tr>
                    <td> <a href="${data.resettingFormLink}" target="_blank">Восстановить пароль</a> </td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>
    </tbody>
</table>