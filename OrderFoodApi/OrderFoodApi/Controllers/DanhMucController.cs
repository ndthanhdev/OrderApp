using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using OrderFoodApi.Entity;
using Microsoft.EntityFrameworkCore;
using OrderFoodApi.Controllers.Shared;

// For more information on enabling MVC for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace OrderFoodApi.Controllers
{
    [Route("[controller]/[action]")]
    public class DanhMucController : Controller
    {
        private readonly OrderFoodContext _db;

        public DanhMucController(OrderFoodContext context)
        {
            _db = context;
        }

        [HttpGet]
        public JsonResult GetDanhMucs()
        {
            var danhMucs = _db.DanhMucs.ToArray();
            return Json(danhMucs);
        }

        [HttpPost]
        public async Task<IActionResult> AddDanhMuc([FromBody] DanhMucAddDanhMucData data)
        {
            if (await _db.DanhMucs.FirstOrDefaultAsync(dm => dm.TenDanhMuc == data.TenDanhMuc) != null)
            {
                return Json(new ResponseData(1, "tên danh mục đã tồn tại"));
            }
            var danhMuc = new DanhMuc()
            {
                TenDanhMuc = data.TenDanhMuc
            };
            await _db.DanhMucs.AddAsync(danhMuc);
            await _db.SaveChangesAsync();
            return Json(new ResponseData(0, danhMuc));
        }

        [HttpPost]
        public async Task<IActionResult> UpdateDanhMuc([FromBody] DanhMuUpdateDanhMucData data)
        {
            var danhMucIndb = await _db.DanhMucs.FirstOrDefaultAsync(dm => dm.DanhMucId == data.DanhMuc.DanhMucId);
            if ((await _db.DanhMucs.FirstOrDefaultAsync(dm => dm.DanhMucId == data.DanhMuc.DanhMucId)) == null)
            {
                return BadRequest("Ten danh muc khong ton tai");
            }
            if (await _db.QuanLys.FirstOrDefaultAsync(
                    ql => ql.QuanLyId == data.QuanLy.QuanLyId && ql.Password == data.QuanLy.Password) == null)
            {
                return Unauthorized();
            }
            danhMucIndb.TenDanhMuc = data.DanhMuc.TenDanhMuc;
            await _db.SaveChangesAsync();
            return Json(danhMucIndb);
        }

        [HttpGet("{id}")]        
        public async Task<IActionResult> Delete(int id)
        {
            var danhMucInDb = await _db.DanhMucs.FirstOrDefaultAsync(dm => dm.DanhMucId == id);
            if (danhMucInDb == null)
            {
                return Json(new ResponseData(1, "Danh mục không tồn tại"));
            }
            _db.DanhMucs.Remove(danhMucInDb);
            await _db.SaveChangesAsync();
            return Json(new ResponseData(0));
        }


        public class DanhMucAddDanhMucData
        {
            public QuanLy QuanLy { get; set; }
            public string TenDanhMuc { get; set; }
        }

        public class DanhMuUpdateDanhMucData
        {
            public QuanLy QuanLy { get; set; }
            public DanhMuc DanhMuc { get; set; }
        }


    }
}
