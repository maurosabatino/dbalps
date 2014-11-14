<html>
<head>
<title>tablesorter: 296 rows</title>
	
	
	
	<script type="text/javascript" src="js/jquery-latest.js"></script>
	
	<script type="text/javascript" src="js/jquery.tablesorter.js"></script>
	<script type="text/javascript" src="js/jquery.tablesorter.pager.js"></script>

	<script type="text/javascript">
	$(function() {
		$("table")
			.tablesorter({debug: true})
			.tablesorterPager({container: $("#pager")});
	});
	</script>	
</head>

<body>
<table cellspacing="0" id="large">
	<thead>
		<tr>
			<th>Name</th>
			<th>Major</th>
			<th>Sex</th>
			<th>English</th>
			<th>Japanese</th>
			<th>Calculus</th>
			<th>Geometry</th>

		</tr>
	</thead>
	<tfoot>
		<tr>
			<th>Name</th>
			<th>Major</th>
			<th>Sex</th>
			<th>English</th>
			<th>Japanese</th>
			<th>Calculus</th>
			<th>Geometry</th>

		</tr>
	</tfoot>
	<tbody>
		<tr>
			<td>Student01</td>
			<td>Languages</td>
			<td>male</td>

			<td>80</td>
			<td>70</td>
			<td>75</td>
			<td>80</td>
		</tr>
		<tr>
			<td>Student02</td>

			<td>Mathematics</td>
			<td>male</td>
			<td>90</td>
			<td>88</td>
			<td>100</td>
			<td>90</td>

		</tr>
		<tr>
			<td>Student03</td>
			<td>Languages</td>
			<td>female</td>
			<td>85</td>
			<td>95</td>

			<td>80</td>
			<td>85</td>
		</tr>
		<tr>
			<td>Student04</td>
			<td>Languages</td>
			<td>male</td>

			<td>60</td>
			<td>55</td>
			<td>100</td>
			<td>100</td>
		</tr>
		<tr>
			<td>Student05</td>

			<td>Languages</td>
			<td>female</td>
			<td>68</td>
			<td>80</td>
			<td>95</td>
			<td>80</td>

		</tr>
		<tr>
			<td>Student06</td>
			<td>Mathematics</td>
			<td>male</td>
			<td>100</td>
			<td>99</td>

			<td>100</td>
			<td>90</td>
		</tr>
		<tr>
			<td>Student07</td>
			<td>Mathematics</td>
			<td>male</td>

			<td>85</td>
			<td>68</td>
			<td>90</td>
			<td>90</td>
		</tr>
		<tr>
			<td>Student08</td>

			<td>Languages</td>
			<td>male</td>
			<td>100</td>
			<td>90</td>
			<td>90</td>
			<td>85</td>

		</tr>
		<tr>
			<td>Student09</td>
			<td>Mathematics</td>
			<td>male</td>
			<td>80</td>
			<td>50</td>

			<td>65</td>
			<td>75</td>
		</tr>
		<tr>
			<td>Student10</td>
			<td>Languages</td>
			<td>male</td>

			<td>85</td>
			<td>100</td>
			<td>100</td>
			<td>90</td>
		</tr>
		<tr>
			<td>Student11</td>

			<td>Languages</td>
			<td>male</td>
			<td>86</td>
			<td>85</td>
			<td>100</td>
			<td>100</td>

		</tr>
		<tr>
			<td>Student12</td>
			<td>Mathematics</td>
			<td>female</td>
			<td>100</td>
			<td>75</td>

			<td>70</td>
			<td>85</td>
		</tr>
</table>
<div id="pager" class="pager">
	<form>
		<img src="../addons/pager/icons/first.png" class="first"/>
		<img src="../addons/pager/icons/prev.png" class="prev"/>
		<input type="text" class="pagedisplay"/>
		<img src="../addons/pager/icons/next.png" class="next"/>
		<img src="../addons/pager/icons/last.png" class="last"/>
		<select class="pagesize">
			<option selected="selected"  value="10">10</option>
			<option value="20">20</option>
			<option value="30">30</option>
			<option  value="40">40</option>
		</select>
	</form>
</div>
</body>

</html>