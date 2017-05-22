using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace OrderFoodApi.Entity
{
    public class DonHang
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int DonHangId { get; set; }

        [ForeignKey(nameof(Sdt))]
        public KhachHang KhachHang { get; set; }

        [JsonIgnore]
        public string Sdt { get; set; }

        [JsonConverter(typeof(CustomDateTimeConverter))]
        public DateTime Ngay { get; set; }

        public TinhTrangDonHang TinhTrangDonHang { get; set; }

        public List<ChiTietDonHang> ChiTietDonHangs { get; set; }
    }

    public class CustomDateTimeConverter : IsoDateTimeConverter
    {
        public CustomDateTimeConverter()
        {
            base.DateTimeFormat = "dd-MM-yyyy";
        }
    }
}
