
	// nombre de ligne de produit
	var nb_line =  $("table tbody tr").length;
	
	// on parcours le tableau
	for (var i=0; i <= nb_line; ++i) {
		
		if (i % 2 == 0) continue;
		
		// cas impair
		var left_side = $("table tbody tr:nth-child("+i+")").find('td:first-child').html();
		var right_side = $("table tbody tr:nth-child("+i+")").find('td:last-child').html();

		$("table tbody tr:nth-child("+i+")").find('td:first-child').html(right_side);
		$("table tbody tr:nth-child("+i+")").find('td:last-child').html(left_side);
	}
