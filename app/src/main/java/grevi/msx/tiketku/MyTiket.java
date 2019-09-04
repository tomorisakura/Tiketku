package grevi.msx.tiketku;

public class MyTiket {

    String nama_wisata,lokasi_wisata,jumlah_tiket;

    public MyTiket() {
    }

    public MyTiket(String nama_wisata, String lokasi_wisata, String jumlah_tiket) {
        this.nama_wisata = nama_wisata;
        this.lokasi_wisata = lokasi_wisata;
        this.jumlah_tiket = jumlah_tiket;
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public void setNama_wisata(String nama_wisata) {
        this.nama_wisata = nama_wisata;
    }

    public String getLokasi_wisata() {
        return lokasi_wisata;
    }

    public void setLokasi_wisata(String lokasi_wisata) {
        this.lokasi_wisata = lokasi_wisata;
    }

    public String getJumlah_tiket() {
        return jumlah_tiket;
    }

    public void setJumlah_tiket(String jumlah_tiket) {
        this.jumlah_tiket = jumlah_tiket +" Tickets";
    }
}
