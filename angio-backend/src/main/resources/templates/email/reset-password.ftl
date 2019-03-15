<p>Добрый день!</p>
<p>Для вашей учетной записи ${data.email} поступил запрос на восстановление пароля. Ваша учетная запись заблокирована до восстановления пароля.</p>
<p>
    Теперь Вам необходимо нажать на кнопку ниже и, в открывшейся форме, ввести код восстановления, а также задать новый пароль для учетной записи.
</p>
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