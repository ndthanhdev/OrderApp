using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OrderFoodApi.Entity;

namespace OrderFoodApi.Controllers
{
    [Route("[controller]/[action]")]
    public class SeedController : Controller
    {
        private readonly OrderFoodContext _context;

        public SeedController(OrderFoodContext context)
        {
            _context = context;
        }

        public async Task<IActionResult> Index()
        {

            await InnerSeedDanhMucMonAn();

            await InnerSeedDonHang();

            await InnerSeedQuanLy();

            return Ok("Seeded");
        }

        private async Task InnerSeedQuanLy()
        {
            if (_context.QuanLys.Any())
            {
                return;
            }
            await _context.QuanLys.AddAsync(new QuanLy()
            {
                QuanLyId = "admin",
                Password = "123456"
            });
            await _context.SaveChangesAsync();
        }

        private async Task InnerSeedDonHang()
        {
            if (_context.KhachHangs.Any())
            {
                return;
            }

            KhachHang khachHang = new KhachHang()
            {
                Sdt = "0123456789",
                DiaChi = "19 Nguyễn Hữu Thọ",
                Ten = "Thanh"
            };
            _context.KhachHangs.Add(khachHang);
            await _context.SaveChangesAsync();

            DonHang donHang = new DonHang()
            {
                Sdt = "0123456789",
                TinhTrangDonHang = TinhTrangDonHang.ChoXuLy,
                ChiTietDonHangs = new List<ChiTietDonHang>()
                {
                    new ChiTietDonHang()
                    {
                        MonAnId=1,
                        DonGia=10000,
                        SoLuong=1
                    },
                    new ChiTietDonHang()
                    {
                        MonAnId=2,
                        DonGia=10000,
                        SoLuong=2
                    }
                }
            };

            await _context.DonHangs.AddAsync(donHang);
            await _context.SaveChangesAsync();
        }

        private async Task InnerSeedDanhMucMonAn()
        {
            if (_context.DanhMucs.Any())
            {
                return;
            }

            // 1
            var danhMuc1 = new DanhMuc()
            {
                TenDanhMuc = "Thức uống",
            };
            await _context.DanhMucs.AddAsync(danhMuc1);
            await _context.MonAns.AddAsync(new MonAn()
            {
                TenMonAn = "Cà phê sữa",
                Gia = 10000,
                MoTa = "Cà phê từ ngũ cốc rang cháy + sữa hết date bao ung thư",
                DanhMuc = danhMuc1
            });
            await _context.MonAns.AddAsync(new MonAn()
            {
                TenMonAn = "Sữa tươi",
                Gia = 10000,
                MoTa = "Sữa chứa melamin",
                DanhMuc = danhMuc1
            });



            // 2
            var danhMuc2 = new DanhMuc()
            {
                TenDanhMuc = "Đồ ăn",
            };
            await _context.AddAsync(danhMuc2);
            await _context.MonAns.AddAsync(new MonAn()
            {
                TenMonAn = "Bánh mì ốp la",
                Gia = 10000,
                MoTa = "Bánh mì mốc + trứng made in China"
                ,
                DanhMuc = danhMuc2
            });
            await _context.MonAns.AddAsync(new MonAn()
            {
                TenMonAn = "Mì tôm",
                Gia = 10000,
                MoTa = "Mì thoy ko có tôm đâu ahihi",
                DanhMuc = danhMuc2
            });

            // 3
            var danhMuc3 = new DanhMuc()
            {
                TenDanhMuc = "Lẩu",
            };
            await _context.AddAsync(danhMuc3);
            await _context.MonAns.AddAsync(new MonAn()
            {
                TenMonAn = "Lẩu hải sản",
                Gia = 10000,
                MoTa = "lẩu từ Hải sản tươi sống Vũng Áng",
                DanhMuc = danhMuc3
            });

            await _context.SaveChangesAsync();
        }



    }
}