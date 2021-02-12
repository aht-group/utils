$(function() {
	/**
	 * Since a transition to height: auto is not possible, we need to determine the menu height on runtime
	 * the moment the menu is displayed, i.e. either when the page is loaded or when the display size is
	 * scaled down below the threshold at which the top navigation is displayed as burger menu.
	 * 
	 * We also need to set or remove a CSS class to show or hide the menu when clicking the burger menu icon.
	 */
	function calculateMenuHeight() {
		let cssRules = '@media (max-width: 768px) {';
		
		dropdowns.each((index, element) => cssRules += '#' + $(element).attr('id') + '.jeesl-active { height: ' + ($(element).children('.jeesl-dropdown-item').length * 50 + 30) + 'px; }');
		
		style.html(cssRules + '}');
	}
	
	function toggleMenu() {
		menuButtons.filter((index, button) => button !== this).removeClass('jeesl-active').siblings('.jeesl-dropdown-list').removeClass('jeesl-active');
		
		$(this).toggleClass('jeesl-active').siblings('.jeesl-dropdown-list').toggleClass('jeesl-active');
	}
	
	let style = $('<style>').prop('type', 'text/css').appendTo('head');
	
	let menuButtons = $('.jeesl-menu-bar-button');
	let dropdowns = $('.jeesl-dropdown-list').attr('id', (index, oldValue) => 'jeesl-dropdown-' + index);
	calculateMenuHeight();
	
	menuButtons.click(toggleMenu);
});