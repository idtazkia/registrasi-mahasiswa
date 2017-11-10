$(document).ready(function(){
    var urlProvinsi = "/api/provinsi";
    var urlSekolah ="/api/sekolah";

    var templateUrlKabupaten = "/api/provinsi/{provinsi}/kabupaten";



    var urlKabupaten = null;

    var sekolah = null;
    var provinsi = null;
    var kabupatenKota = null;
    var kokab = null;


    var inputSekolah = $("#sekolah");
    var inputProvinsi = $("#provinsi");
    var inputKabupatenKota = $("#kabupatenKota");
    var inputKokab = $("#kokab");

    var resetInput = function(inputField){
        inputField.val('');
        inputField.prop('disabled', true);
    };

    resetInput(inputKabupatenKota);


    inputProvinsi.typeahead({
        displayText: function(item){ return item.nama;},
        source: _.debounce(function(cari, process){
            provinsi = null;
            resetInput(inputKabupatenKota);
            $.get(urlProvinsi, {nama: cari}, function(hasil){
                process(hasil);
            }, "json");
        }, 500),
        afterSelect: function(pilihan){
            provinsi = pilihan;
            inputKabupatenKota.prop('disabled', false);
            urlKabupaten = _.replace(templateUrlKabupaten, '{provinsi}', provinsi.id);
        }
    });

    inputKabupatenKota.typeahead({
        displayText: function(item){ return item.nama;},
        source: _.debounce(function(cari, process){
            kabupatenKota = null;
            $.get(urlKabupaten, {nama: cari}, function(hasil){
                process(hasil);
            }, "json");
        }, 500),

    });

    inputSekolah.typeahead({
        displayText: function(item){ return item.nama;},
        source: _.debounce(function(cari, process){
            sekolah = null;
            $.get(urlSekolah, {nama: cari}, function(hasil){
                process(hasil.content);
            }, "json");
        }, 500),

    });

    inputKokab.typeahead({
        displayText: function(item){ return item.nama;},
        source: _.debounce(function(cari, process){
            kokab = null;
            $.get(urlKabupaten, {nama: cari}, function(hasil){
                process(hasil);
            }, "json");
        }, 500),

    });

});