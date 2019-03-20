<h1>${data.additionalInfo.name}</h1>
<p>
    <b>Анализ номер №${data.id}</b><br/>
    <b>Дата анализа:</b> ${data.analyseDate?string["dd.MM.yyyy"]}<br/>
    <b>Диагност:</b> ${data.additionalInfo.diagnostician.fullName.lastname} ${data.additionalInfo.diagnostician.fullName.firstname} ${data.additionalInfo.diagnostician.fullName.patronymic}<br/>
    <b>Описание:</b> ${data.additionalInfo.fullDescription}<br/>
    <b>Тип:</b> ${data.additionalInfo.type.description}<br/>
    <b>Комментарий:</b> ${data.additionalInfo.comment}<br/>
    <br/>
    <b>Пациент:</b> ${data.patient.fullName.lastname} ${data.patient.fullName.firstname} ${data.patient.fullName.patronymic}<br/>
    <b>Дата рождения:</b> ${data.patient.bday?string["dd.MM.yyyy"]}<br/>
    <b>Телефон:</b> ${data.patient.phone}<br/>
    <b>Полис:</b> ${data.patient.policy}<br/>
    <b>Электронная почта:</b> ${data.patient.email}<br/>
    <b>Место жительства:</b> ${data.patient.locationAddress}<br/>
    <b>Место работы/учебы:</b> ${data.patient.workAddress}<br/>
</p>
<h2>Геометрическая характеристика сосудистой системы</h2>
<table border="0" cellpadding="4pt" width="100%" align="center">
    <tr>
        <td><img src="${data.originalImage.filename}" width="150pt"/></td>
        <td><img src="${data.geometricAnalyse.binarizedImage.filename}" width="150pt"/></td>
        <td><img src="${data.geometricAnalyse.skeletonizedImage.filename}" width="150pt"/></td>
    </tr>
    <tr>
        <td style="font-size: 10pt">Оригинал</td>
        <td style="font-size: 10pt">Бинаризованное</td>
        <td style="font-size: 10pt">Скелетизованное</td>
    </tr>
</table>
<br/>
<table border="1" cellpadding="4pt" width="100%" align="center" style="border-collapse: collapse">
    <caption>Результаты анализа по отдельным сосудам</caption>
    <tr>
        <th>Кол-во сосудов</th>
        <th>Кол-во ветвей</th>
        <th>Средняя извилистость</th>
        <th>Средняя ветвистость</th>
        <th>Общая площадь (px)</th>
        <th>Общая площадь (%)</th>
    </tr>
    <tr>
        <td>${data.geometricAnalyse.vesselsCount}</td>
        <td>${data.geometricAnalyse.branchesCount}</td>
        <td>${data.geometricAnalyse.tortuosityDegreeAvg?string["0.##"]}</td>
        <td>${data.geometricAnalyse.branchingDegreeAvg?string["0.##"]}</td>
        <td>${data.geometricAnalyse.areaSumPx?ceiling}</td>
        <td>${data.geometricAnalyse.areaSumPercent?string["0.##"]}%</td>
    </tr>
</table>
<h2 class="page-break">Степень кровоснабжения сосудистой системы</h2>
<table border="0" cellpadding="4pt" width="100%" align="center">
    <caption><h2>Ишемические зоны и макула</h2></caption>
    <tr>
        <td style="text-align:center; vertical-align: top" width="43%"><img src="${data.bloodFlowAnalyse.ischemiaImage.filename}" width="200pt"/></td>
        <td style="text-align:left; vertical-align: top">
            <b>Площадь макулы:</b> ${data.bloodFlowAnalyse.macula.area?ceiling}<br/>
            <b>Радиус макулы:</b> ${data.bloodFlowAnalyse.macula.radius?ceiling}<br/>
            <b>Центр макулы:</b> {${data.bloodFlowAnalyse.macula.x?ceiling}; ${data.bloodFlowAnalyse.macula.y?ceiling}}<br/>
            <br/>
            <table border="1" style="border-collapse: collapse" width="100%">
                <caption>Ишемические зоны</caption>
                <tr>
                    <th>№</th>
                    <th>Площадь (px)</th>
                    <th>x (px)</th>
                    <th>y (px)</th>
                </tr>
                <#list data.bloodFlowAnalyse.ischemias as ischemia>
                    <tr>
                        <td>${ischemia.zoneNumber}</td>
                        <td>${ischemia.area?ceiling}</td>
                        <td>${ischemia.x?ceiling}</td>
                        <td>${ischemia.y?ceiling}</td>
                    </tr>
                </#list>
            </table>
        </td>
    </tr>
</table>
<table border="0" cellpadding="4pt" width="100%" align="center">
    <caption><h2>Плотность капилляров в облости макулы</h2></caption>
    <tr>
        <td style="text-align:center; vertical-align: top" width="43%"><img src="${data.bloodFlowAnalyse.densityImage.filename}" width="200pt"/></td>
        <td style="text-align:left; vertical-align: top">
            <table border="1" style="border-collapse: collapse" width="100%">
                <tr>
                    <th>№</th>
                    <th>Плотность</th>
                </tr>
                <#list data.bloodFlowAnalyse.densities as density>
                    <tr>
                        <td>${density.sectorNumber}</td>
                        <td>${density.density?string["0.##"]}</td>
                    </tr>
                </#list>
            </table>
        </td>
    </tr>
</table>
<h2>Заключение</h2>
<p>${data.additionalInfo.conclusion}</p>
