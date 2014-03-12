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


/* BREADCRUMBS */
ul.aupHeadlineBreadcrumb {
  float:right;
  position: relative;
  right: -20px;
  top: -6px;
}
ul.aupHeadlineBreadcrumb,
.aupHeadlineBreadcrumb ul {
  list-style: none;
  padding: 0;
  margin: 0;
}
/* .aupHeadlineBreadcrumb li a , .aupHeadlineBreadcrumb li ul li a { */
.aupHeadlineBreadcrumb a {
  position: relative;

  /* display: inline-block; */
  display: block;
  white-space: nowrap;

  background-color: #DDDDDD;
  background-image: linear-gradient(to right, ${colorDark}, ${colorLight});
  color: #FFFFFF;
  padding: 7px 1em 6px 2em;
}

.aupHeadlineBreadcrumb a:hover {
    background: none repeat scroll 0 0 ${colorDark};
}
/* .aupHeadlineBreadcrumb a:after, .aupHeadlineBreadcrumb a:before */
.aupHeadlineBreadcrumb > li > a:after, .aupHeadlineBreadcrumb > li > a:before,
.aupHeadlineBreadcrumb > li > ul > li > a:after, .aupHeadlineBreadcrumb > li > ul > li > a:before {
    border-bottom: 1.0em solid rgba(0, 0, 0, 0);
    border-left: 1em solid;
    border-top: 1.0em solid rgba(0, 0, 0, 0);
    content: "";
    margin-top: -1.0em;
    position: absolute;
    right: -1em;
    top: 50%;
}
/* .aupHeadlineBreadcrumb a:after { */
.aupHeadlineBreadcrumb > li > a:after, .aupHeadlineBreadcrumb > li > ul > li > a:after {
    border-left-color: ${colorLight};
    z-index: 2;
}
/* .aupHeadlineBreadcrumb a:before { */
.aupHeadlineBreadcrumb > li > a:before, .aupHeadlineBreadcrumb > li > ul > li > a:before {
    border-left-color: ${colorLight};
    right: -1.1em;
    z-index: 1;
}
/* .aupHeadlineBreadcrumb a:hover:after { */
.aupHeadlineBreadcrumb > li > a:hover:after, .aupHeadlineBreadcrumb > li > ul > li > a:hover:after  {
    border-left-color: ${colorDark};
}
.aupHeadlineBreadcrumb > li:last-child a {
  /* Next line trims the last child */
  overflow: hidden;
}
.aupHeadlineBreadcrumb .current, .aupHeadlineBreadcrumb .current:hover {
    background: none repeat scroll 0 0 rgba(0, 0, 0, 0);
    font-weight: bold;
}
.aupHeadlineBreadcrumb .current:after, .aupHeadlineBreadcrumb .current:before {
    content: normal;
}


.aupHeadlineBreadcrumb > li {
  position: relative;
  float: left;
  text-wrap:none;
}
.aupHeadlineBreadcrumb > li:hover ul {
  opacity: 1;
  visibility: visible;
  -webkit-transition-delay: 0s, 0s;
  -moz-transition-delay: 0s, 0s;
  -o-transition-delay: 0s, 0s;
  transition-delay: 0s, 0s;
}
.aupHeadlineBreadcrumb ul {
  text-align:right;
  right:0;

  opacity: 0;
  visibility: hidden;
  z-index: 1;
  position: absolute;
  -webkit-transition-property: opacity, visibility;
  -moz-transition-property: opacity, visibility;
  -o-transition-property: opacity, visibility;
  transition-property: opacity, visibility;
  -webkit-transition-duration: .2s, 0s;
  -moz-transition-duration: .2s, 0s;
  -o-transition-duration: .2s, 0s;
  transition-duration: .2s, 0s;
  -webkit-transition-delay: 0s, .2s;
  -moz-transition-delay: 0s, .2s;
  -o-transition-delay: 0s, .2s;
  transition-delay: 0s, .2s;
}
