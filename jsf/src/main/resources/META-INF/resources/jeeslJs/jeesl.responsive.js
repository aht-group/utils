/**
 * Since a transition to height: auto is not possible, we need to determine the menu height on runtime
 * the moment the menu is displayed, i.e. either when the page is loaded or when the display size is
 * scaled down below the threshold at which the top navigation is displayed as burger menu.
 * 
 * We also need to set or remove a CSS class to show or hide the menu when clicking the burger menu icon.
 */
let style;

function calculateMenuHeight() {
	let cssRules = '@media (max-width: 768px) {';
	
	$('.jeesl-dropdown-list').each((index, element) => cssRules += '#' + $(element).attr('id') + '.jeesl-active { height: ' + ($(element).children('.jeesl-dropdown-item').length * 50 + 30) + 'px; }');
	
	style.append(cssRules + '}');
}

function toggleMenu() {
	$('.jeesl-menu-bar-button').filter((index, button) => button !== this).removeClass('jeesl-active').siblings('.jeesl-dropdown-list').removeClass('jeesl-active');
	
	$(this).toggleClass('jeesl-active').siblings('.jeesl-dropdown-list').toggleClass('jeesl-active');
}

function reloadStatusBar() {
	let newButtons = $('.jeesl-status-bar .jeesl-menu-bar-button');
	let newDropdowns = $('.jeesl-status-bar .jeesl-dropdown-list').attr('id', (index, oldValue) => 'jeesl-dropdown-' + ($('.jeesl-dropdown-list').length + index));
	
	calculateMenuHeight(newDropdowns);
	newButtons.click(toggleMenu);
}

$(function() {
	style = $('<style>').prop('type', 'text/css').appendTo('head');
	
	let menuButtons = $('.jeesl-menu-bar-button');
	let dropdowns = $('.jeesl-dropdown-list').attr('id', (index, oldValue) => 'jeesl-dropdown-' + index);
	
	calculateMenuHeight(dropdowns);
	menuButtons.click(toggleMenu);
});