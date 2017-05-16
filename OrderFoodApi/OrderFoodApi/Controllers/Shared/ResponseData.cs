using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace OrderFoodApi.Controllers.Shared
{
    public class ResponseData
    {
        public int Status { get; set; }
        public object Data { get; set; }
        public string Message { get; set; }

        public ResponseData(int status, object data = null, string message = null)
        {
            Status = status;
            Data = data;
            Message = message;
        }
    }
}
