$(function() 
{
	var list = new Array();
	var arr1 = new Array();
	var arr2 = new Array();
	var arr3 = new Array();
	var arr4 = new Array();
	
	for(var i=0;i<=16;i++)
	{
		list.push("S"+(i+1));		
	}
	

	for(var j=0;j<list.length;j+=4)
	{
		if(list[j+1]!=null){arr1.push(list[j+0]);}
		if(list[j+2]!=null){arr2.push(list[j+1]);}	
		if(list[j+3]!=null){arr3.push(list[j+2]);}	
		if(list[j+4]!=null){arr4.push(list[j+3]);}
	}
	
	var txt1 = $('#txtlzr1');
	effect: 'slideTop';
	txt1.textualizer(arr1, { duration: 5000 });
	txt1.textualizer('start');
	
	var txt2 = $('#txtlzr2');
	effect: 'slideTop';
	txt2.textualizer(arr2, { duration: 5000 });
	txt2.textualizer('start');
	
	var txt3 = $('#txtlzr3');
	effect: 'slideTop';
	txt3.textualizer(arr3, { duration: 5000 });
	txt3.textualizer('start');
	
	var txt4 = $('#txtlzr4');
	effect: 'slideTop';
	txt4.textualizer(arr4, { duration: 5000 });
	txt4.textualizer('start');
});