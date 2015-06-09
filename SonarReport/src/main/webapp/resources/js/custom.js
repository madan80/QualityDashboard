$(window).load(function(){
    var $table  = $('#dashboard-report'),        
        $rows   = $('tbody > tr', $table); 

    $rows.sort(function(a, b) {
        var keyA = $('td',a).data('category');
        var keyB = $('td',b).data('category');
        return (keyA > keyB) ? 1 : 0;  
    });

    $rows.each(function(index, row){
      $table.append(row);                  
    });
});
