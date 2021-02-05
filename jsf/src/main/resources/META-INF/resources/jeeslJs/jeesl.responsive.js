$(function() {
	/**
	 * Due to its nested structure and the absolute display we have to set button position manually to
	 * display breadcrumbs horizontally next to each other.
	 */
	
	let burgerMenuButtons = $('.jeesl-breadcrumb-header .jeesl-burger-menu-button');
	
	function calculateBreadcrumbPosition() {
		burgerMenuButtons.each(function (index) {
			let button = $(this);
			button.css('margin-right', button.closest('li:not(.jeesl-burger-menu-button)')
											 .nextAll()
											 .find('.jeesl-burger-menu-button')
											 .toArray()
											 .map(subsequentButton => $(subsequentButton).outerWidth())
											 .reduce((accumulator, currentValue) => accumulator + currentValue, 0) + 'px');
		});
	}
	
	/**
	 * Since a transition to height: auto is not possible, we need to determine the menu height on runtime
	 * the moment the menu is displayed, i.e. either when the page is loaded or when the display size is
	 * scaled down below the threshold at which the top navigation is displayed as burger menu.
	 * 
	 * We also need to set or remove a CSS class to show or hide the menu when clicking the burger menu icon.
	 */
	function toggleMenu() {
		menuButtons.filter((index, button) => button !== this).parent().removeClass('jeesl-active');
		
		$(this).parent().toggleClass('jeesl-active');
	}

	function calculateMenuHeight() {
		let maxHeight = 0;
		let cssRules = '@media (max-width: 768px) {';
		
		menuButtons.each(function(index) {
			let icon = $(this);
			
			maxHeight = icon.outerHeight() + icon.siblings('li').length * 50 + 30;
			
			cssRules += '#burger-menu-' + index + '.jeesl-active { height: ' + maxHeight + 'px; }';
		});
		
		style.html(cssRules + '}');
	}
	
	let style = $('<style>').prop('type', 'text/css').appendTo('head');
	
	let menuButtons = $('.jeesl-burger-menu-button');
	menuButtons.parent().attr('id', (index, oldValue) => 'burger-menu-' + index);

	calculateMenuHeight();
	calculateBreadcrumbPosition();
	
	menuButtons.click(toggleMenu);
});