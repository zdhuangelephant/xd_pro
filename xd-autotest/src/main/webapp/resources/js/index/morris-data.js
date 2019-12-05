// Morris.js Charts sample data for SB Admin template

$(document).ready(function() {

    // Area Chart
    Morris.Area({
        element: 'morris-area-chart',
        data: [{
            period: '2010 Q1',
            total: 3000,
            success: 2830,
            fail: 170
        }, {
            period: '2010 Q2',
            total: 2778,
            success: 2294,
            fail: 2441
        }, {
            period: '2010 Q3',
            total: 4912,
            success: 1969,
            fail: 2501
        }, {
            period: '2010 Q4',
            total: 5689,
            success: 3597,
            fail: 1000
        }, {
            period: '2011 Q1',
            total: 6810,
            success: 1914,
            fail: 2293
        }, {
            period: '2011 Q2',
            total: 5670,
            success: 4293,
            fail: 1881
        }, {
            period: '2011 Q3',
            total: 4820,
            success: 3795,
            fail: 1588
        }, {
            period: '2011 Q4',
            total: 15073,
            success: 5967,
            fail: 5175
        }, {
            period: '2012 Q1',
            total: 10687,
            success: 4460,
            fail: 2028
        }, {
            period: '2012 Q2',
            total: 8432,
            success: 5713,
            fail: 1791
        }],
        xkey: 'period',
        ykeys: ['total', 'success', 'fail'],
        labels: ['Total', 'Success', 'Fail'],
        lineColors:['blue','green', 'red'],
        pointSize: 2,
        hideHover: 'auto',
        resize: true
    });

});
