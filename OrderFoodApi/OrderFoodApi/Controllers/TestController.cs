using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace OrderFoodApi.Controllers
{
    public class TestController:Controller
    {        
        [HttpGet("/test")]
        public async Task<IActionResult> Test()
        {
            await Task.Yield();
            return Ok("ok");
        }
    }
}
