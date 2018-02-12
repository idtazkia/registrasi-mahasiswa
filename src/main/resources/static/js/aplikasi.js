$(document).ready(function(){
    var urlProvinsi = "/api/provinsi";
    var urlSekolah ="/api/sekolah";
    var urlKokabawal = "/api/kokabawal";
    var urlPendaftar = "/api/pendaftar";
    var urlCariKokab = "/api/cariKokab";

    var templateUrlKabupaten = "/api/provinsi/{provinsi}/kabupaten";

    var templateUrlTagihan = "/tagihan/list?pendaftar={pendaftar}";



    var urlKabupaten = null;
    var urltagihan =null;

    var sekolah = null;
    var provinsi = null;
    var kabupatenKota = null;
    var kokab = null;
    var pendaftar = null;
    var tagihan =  null;
    var cariSekolah =  null;
    var cariKokab =  null;


    var inputSekolah = $("#sekolah");
    var inputProvinsi = $("#provinsi");
    var inputKabupatenKota = $("#kabupatenKota");
    var inputKokabawal = $("#kokabawal");
    var inputHiddenKokab = $("input[name=idKabupatenKota]");
    //uploadSmartTest
    var inputCariSekolah = $("#cariSekolah");
    var inputCariKokab = $("#cariKokab");
    var inputHiddenIdKotakabupaten = $("input[name=kabupatenKota]");
    var inputHiddenIdSekolah = $("input[name=idSekolah]");

    var inputPendaftar = $("#pendaftar");
    var inputTagihan = $("#id");

    var resetInput = function(inputField){
        inputField.val('');
        inputField.prop('disabled', true);
    };

    // resetInput(inputKabupatenKota);


    inputProvinsi.typeahead({
        displayText: function(item){ return item.nama;},
        source: _.debounce(function(cari, process){
            provinsi = null;
            // resetInput(inputKabupatenKota);
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
        afterSelect: function(pilihan) {
            console.log( pilihan.id);}
    });

    inputSekolah.typeahead({
        displayText: function(item){ return item.nama;},
        source: _.debounce(function(cari, process){
            sekolah = null;
            $.get(urlSekolah, {nama: cari}, function(hasil){
                process(hasil.content);
            }, "json");
        }, 500)

    });
    inputCariKokab.typeahead({
        displayText: function(item){ return item.nama;},
        source: _.debounce(function(cari, process){
            sekolah = null;
            $.get(urlCariKokab, {nama: cari}, function(hasil){
                process(hasil.content);
            }, "json");
        }, 500),
        afterSelect: function(pilihan) {
            inputHiddenIdKotakabupaten.val(pilihan.id);
            console.log( pilihan.id);

    }

    });
    inputCariSekolah.typeahead({
        displayText: function(item){ return item.nama;},
        source: _.debounce(function(cari, process){
            sekolah = null;
            $.get(urlSekolah, {nama: cari}, function(hasil){
                process(hasil.content);
            }, "json");
        }, 500),
        afterSelect: function(pilihan) {
            inputHiddenIdSekolah.val(pilihan.id);
            console.log( pilihan.id);
    }

    });

    inputKokabawal.typeahead({
        displayText: function(item){ return item.nama;},
        source: _.debounce(function(cari, process){
            kokabawal = null;
            $.get(urlKokabawal, {nama: cari}, function(hasil){
                process(hasil);
            }, "json");
        }, 500),
        afterSelect: function(pilihan) {
            inputHiddenKokab.val(pilihan.id);
        }
    });

    //pendaftar(tagihan)
    inputPendaftar.typeahead({
        displayText: function(item){ return item.nomorRegistrasi;},
        source: _.debounce(function(cari, process){
            pendaftar = null;
            $.get(urlPendaftar, {nomorRegistrasi: cari}, function(hasil){
                process(hasil.content);
            }, "json");
        }, 500),
        afterSelect: function(pilihan) {
            console.log( pilihan.id);
            document.getElementById('nomorRegistrasi').innerHTML = pilihan.nomorRegistrasi;
            document.getElementById('nama').innerHTML = pilihan.nama;
            document.getElementById('email').innerHTML = pilihan.email;
            document.getElementById('noHp').innerHTML = pilihan.noHp;
            inputTagihan.val(pilihan.id);
        // http request ke api query tagihan by pendaftar
            tagihan = pilihan;
            urltagihan = _.replace(templateUrlTagihan, '{pendaftar}', pilihan.id);
            window.open(urltagihan);

        }
    });

});