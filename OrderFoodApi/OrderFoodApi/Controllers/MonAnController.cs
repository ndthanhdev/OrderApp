using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using OrderFoodApi.Entity;
using OrderFoodApi.Controllers.Shared;

namespace OrderFoodApi.Controllers
{
    [Route("[controller]/[action]")]
    public class MonAnController : Controller
    {
        private readonly OrderFoodContext _context;

        public MonAnController(OrderFoodContext context)
        {
            _context = context;
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetMonAns(int id)
        {
            var danhMuc = await _context.DanhMucs.Include(dm => dm.MonAns).FirstOrDefaultAsync(dm => dm.DanhMucId == id);
            return Json(danhMuc.MonAns);
        }

        [HttpPost]
        public async Task<IActionResult> AddMonAn([FromBody] AddMonAnData data)
        {
            if (await _context.QuanLys.FirstOrDefaultAsync(
                    ql => ql.QuanLyId == data.QuanLy.QuanLyId && ql.Password == data.QuanLy.Password) == null)
            {
                return Json(new ResponseData(1));
            }
            var innerDanhMuc = await _context.DanhMucs.FirstOrDefaultAsync(dm => dm.DanhMucId == data.MonAn.DanhMucId);
            if (innerDanhMuc == null)
            {
                return Json(new ResponseData(2,"khong co danh muc nay"));
            }

            var innerMonAn = await _context.MonAns.FirstOrDefaultAsync(m => m.TenMonAn.ToLower().Equals(data.MonAn.TenMonAn));
            if (innerMonAn != null)
            {
                return Json(new ResponseData(3, "Tên món ăn đã tồn tại"));
            }

            await _context.AddAsync(data.MonAn);
            await _context.SaveChangesAsync();

            return Json(new ResponseData(0, data.MonAn));
        }

        public class AddMonAnData
        {
            public QuanLy QuanLy { get; set; }
            public MonAn MonAn { get; set; }
        }
    }
}