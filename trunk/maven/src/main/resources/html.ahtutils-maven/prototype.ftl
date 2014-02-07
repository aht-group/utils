/*
Created by AhtUtils Prototype. http://ahtutils.sourceforge.net
Please do not modify this file to avoid inconsistencies!

These parameters a defined by the custom maven configuration:
dark: ${colorDark}
light: ${colorLight}
*/


a { color: ${colorDark};}

.header ul li.active > a {
border-bottom: 2px solid ${colorDark};
color: ${colorDark};
}

.header li a:hover{
border-bottom: 2px solid ${colorDark};
color: ${colorDark};
}

.aupHeadline {
background-color: ${colorDark};
}

.sidemenu a {
color: ${colorDark};
}

.sidemenu a:hover{
color: #FFF;
background: ${colorDark};
}

.aupHeadlineBreadcrumb a{
background-image: linear-gradient(to right, ${colorDark}, ${colorLight});
}

.aupHeadlineBreadcrumb{
background: #eee;
border-style: solid;
overflow: hidden;
float: right;
top: -6px;
position: relative;
right: -20px;
}

.aupHeadlineBreadcrumb li{
float: left;
}

.aupHeadlineBreadcrumb a{
padding: 7px 1em 6px 2em;
float: left;
text-decoration: none;
color: #FFFFFF;
position: relative;
background-color: #ddd;
background-image: linear-gradient(to right, ${colorDark}, ${colorLight});
}

.aupHeadlineBreadcrumb li:first-child a{
padding-left: 1em;
}

.aupHeadlineBreadcrumb a:hover{
background: ${colorDark};
}

.aupHeadlineBreadcrumb a::after,
.aupHeadlineBreadcrumb a::before{
content: "";
position: absolute;
top: 50%;
margin-top: -1.5em;
border-top: 1.5em solid transparent;
border-bottom: 1.5em solid transparent;
border-left: 1em solid;
right: -1em;
}

.aupHeadlineBreadcrumb a::after{
z-index: 2;
border-left-color: ${colorLight};
}

.aupHeadlineBreadcrumb a::before{
border-left-color: ${colorLight};
right: -1.1em;
z-index: 1;
}

.aupHeadlineBreadcrumb a:hover::after{
border-left-color: ${colorDark};
}

.aupHeadlineBreadcrumb .current,
.aupHeadlineBreadcrumb .current:hover{
font-weight: bold;
background: none;
}

.aupHeadlineBreadcrumb .current::after,
.aupHeadlineBreadcrumb .current::before{
content: normal;
}