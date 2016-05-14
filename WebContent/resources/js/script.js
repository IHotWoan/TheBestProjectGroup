
//header


/*
document.getElementById("nav01").innerHTML =
"<header id='header'>"+
"<a href='dashboard.xhtml'><img src='best.jpg' alt='Logo placeholder' style='width: 300px; height: 200px;' /></a>"+
"<h1> Administrator </h1>"+
"<h1> \" Username \" </h1>"+
"<p id='date'></p>"+
"<h2><a href='otherpage.xhtml'> Log out</a></h2>"+
"</header>";
*/
document.getElementById('date').innerHTML = Date();


<script>
var now = new Date();
var dayNames = new Array("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday");
var monNames = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
document.getElementById("nav01")("Today's date is " + dayNames[now.getDay()] + " " + monNames[now.getMonth()] + " " + now.getDate() + ", " + now.getFullYear());
</script>