<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <style type="text/css">

        * {
            font-family: Arial, sans-serif;
            font-size: 14pt;
        }

        h1 {
            font-size: 16pt;
            padding-top: 5pt;
            text-align: center;
        }

        h2, h3, h4, h5 {
            padding-top: 5pt;
            text-align: center;
        }

        th {
            font-size: 12pt;
            text-align: center
        }

        td {
            text-align: center;
        }

        #header {
            margin-top: 20pt;
            position: running(header);
            text-align: left;
        }

        #footer {
            position: running(footer);
            text-align: center;
        }

        @page {
            margin-top: 46pt;

            @top-center { content: element(header); }
            @bottom-center { content: element(footer) }
        }

        .page-break {
            page-break-before: always;
        }

        #pagenumber:before {
            content: counter(page);
        }

        #pagecount:before {
            content: counter(pages);
        }
    </style>
</head>
<body>

<div id="header">
    AngioVision
    <hr/>
</div>

<div id="footer">
    <span id="pagenumber"></span> из <span id="pagecount"></span>
</div>

<div class="content">
    <#include contentTemplate>
</div>

</body>
</html>
