/*
	Created by AhtUtils JSF Grid. http://ahtutils.sourceforge.net
	
	Based on 
 	960 Grid System ~ Core CSS.
  	Learn more ~ http://960.gs/

  	Licensed under GPL and MIT.
*/

/*
  Forces backgrounds to span full width,
  even if there is horizontal scrolling.
  Increase this if your layout is wider.

  Note: IE6 works fine without this fix.
*/

body {min-width: ${width}px;}

/* `Container
----------------------------------------------------------------------------------------------------*/
.container_12
{
  margin-left: auto;
  margin-right: auto;
  width: ${width}px;
  padding-bottom: ${doublegutter}px;
}

/* `Grid >> Global
----------------------------------------------------------------------------------------------------*/
[class*="grid"] {
  display: table;
  float: left;
  margin-left: ${gutter}px;
  margin-right: ${gutter}px;
}

.push_1, .pull_1,
.push_2, .pull_2,
.push_3, .pull_3,
.push_4, .pull_4,
.push_5, .pull_5,
.push_6, .pull_6,
.push_7, .pull_7,
.push_8, .pull_8,
.push_9, .pull_9,
.push_10, .pull_10,
.push_11, .pull_11,
.push_12, .pull_12,
.push_13, .pull_13,
.push_14, .pull_14,
.push_15, .pull_15 {
  position: relative;
}

/* Grid >> Children (Alpha ~ First, Omega ~ Last)
----------------------------------------------------------------------------------------------------*/
.alpha {margin-left: 0;}
.omega {margin-right: 0;}

[class*="grid"] {margin-top: ${doublegutter}px;}

[class*="grid"] > div {margin-bottom: ${doublegutter}px;}
[class*="grid"] > div:last-child {margin-bottom: 0px;}

[class*="grid"] > form {margin-bottom: ${doublegutter}px;}
[class*="grid"] > form:last-child {margin-bottom: 0px;}

[class*="auGridSpace"] > div {margin-bottom: ${doublegutter}px;}
[class*="auGridSpace"] > div:last-child {margin-bottom: 0px;}

/* Grid >> 12 Columns
----------------------------------------------------------------------------------------------------*/
.container_12 .grid_1  {width: ${slot1}px;}
.container_12 .grid_2  {width: ${slot2}px;}
.container_12 .grid_3  {width: ${slot3}px;}
.container_12 .grid_4  {width: ${slot4}px;}
.container_12 .grid_5  {width: ${slot5}px;}
.container_12 .grid_6  {width: ${slot6}px;}
.container_12 .grid_7  {width: ${slot7}px;}
.container_12 .grid_8  {width: ${slot8}px;}
.container_12 .grid_9  {width: ${slot9}px;}
.container_12 .grid_10 {width: ${slot10}px;}
.container_12 .grid_11 {width: ${slot11}px;}
.container_12 .grid_12 {width: ${slot12}px;}

/* `Prefix Extra Space >> 12 Columns
----------------------------------------------------------------------------------------------------*/
.container_12 .prefix_1  {padding-left: 80px;}
.container_12 .prefix_2  {padding-left: 160px;}
.container_12 .prefix_3  {padding-left: 240px;}
.container_12 .prefix_4  {padding-left: 320px;}
.container_12 .prefix_5  {padding-left: 400px;}
.container_12 .prefix_6  {padding-left: 480px;}
.container_12 .prefix_7  {padding-left: 560px;}
.container_12 .prefix_8  {padding-left: 640px;}
.container_12 .prefix_9  {padding-left: 720px;}
.container_12 .prefix_10 {padding-left: 800px;}
.container_12 .prefix_11 {padding-left: 880px;}

/* `Suffix Extra Space >> 12 Columns
----------------------------------------------------------------------------------------------------*/
.container_12 .suffix_1  {padding-right: 80px;}
.container_12 .suffix_2  {padding-right: 160px;}
.container_12 .suffix_3  {padding-right: 240px;}
.container_12 .suffix_4  {padding-right: 320px;}
.container_12 .suffix_5  {padding-right: 400px;}
.container_12 .suffix_6  {padding-right: 480px;}
.container_12 .suffix_7  {padding-right: 560px;}
.container_12 .suffix_8  {padding-right: 640px;}
.container_12 .suffix_9  {padding-right: 720px;}
.container_12 .suffix_10 {padding-right: 800px;}
.container_12 .suffix_11 {padding-right: 880px;}

/* `Push Space >> 12 Columns
----------------------------------------------------------------------------------------------------*/
.container_12 .push_1  {left: 80px;}
.container_12 .push_2  {left: 160px;}
.container_12 .push_3  {left: 240px;}
.container_12 .push_4  {left: 320px;}
.container_12 .push_5  {left: 400px;}
.container_12 .push_6  {left: 480px;}
.container_12 .push_7  {left: 560px;}
.container_12 .push_8  {left: 640px;}
.container_12 .push_9  {left: 720px;}
.container_12 .push_10 {left: 800px;}
.container_12 .push_11 {left: 880px;}

/* `Pull Space >> 12 Columns
----------------------------------------------------------------------------------------------------*/
.container_12 .pull_1  {left: -80px;}
.container_12 .pull_2  {left: -160px;}
.container_12 .pull_3  {left: -240px;}
.container_12 .pull_4  {left: -320px;}
.container_12 .pull_5  {left: -400px;}
.container_12 .pull_6  {left: -480px;}
.container_12 .pull_7  {left: -560px;}
.container_12 .pull_8  {left: -640px;}
.container_12 .pull_9  {left: -720px;}
.container_12 .pull_10 {left: -800px;}
.container_12 .pull_11 {left: -880px;}

/* `Clear Floated Elements
----------------------------------------------------------------------------------------------------*/
/* http://sonspring.com/journal/clearing-floats */
.clear {
  clear: both;
  display: block;
  overflow: hidden;
  visibility: hidden;
  width: 0;
  height: 0;
}

/* http://www.yuiblog.com/blog/2010/09/27/clearfix-reloaded-overflowhidden-demystified */

.clearfix:before,
.clearfix:after,
.container_12:before,
.container_12:after {
  content: '.';
  display: block;
  overflow: hidden;
  visibility: hidden;
  font-size: 0;
  line-height: 0;
  width: 0;
  height: 0;
}

.clearfix:after, .container_12:after {clear: both;}

/*
  The following zoom:1 rule is specifically for IE6 + IE7.
  Move to separate stylesheet if invalid CSS is a problem.
*/
.clearfix, .container_12, .container_16 {zoom: 1;}