function updateCellSelection(cell, rowCode, columnCode) {
	$('.selected').removeClass('selected');
	$(cell).addClass('selected');
	selectCell([
		{ name: 'rowCode', value: rowCode },
		{ name: 'columnCode', value: columnCode }
	]);
}