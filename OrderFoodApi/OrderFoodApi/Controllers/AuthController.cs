using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using OrderFoodApi.Entity;
using Microsoft.EntityFrameworkCore;

// For more information on enabling MVC for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace OrderFoodApi.Controllers
{
    //[Route("[controller]/[action]")]
    public class AuthController : Controller
    {
        private readonly OrderFoodContext _db;

        public AuthController(OrderFoodContext context)
        {
            _db = context;
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody]QuanLy quanLy)
        {
            await Task.Yield();
            var innerQuanLy = await _db.QuanLys.FindAsync(quanLy.QuanLyId);
            if (innerQuanLy == null || innerQuanLy.Password != quanLy.Password)
            {
                return Json(false);
            }
            return Json(true);
        }


    }
}
