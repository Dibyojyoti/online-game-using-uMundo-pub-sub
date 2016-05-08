using System;
using System.Runtime.InteropServices;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TKLABplayer
{
    class Program
    {

        [DllImport("kernel32.dll", CharSet = CharSet.Auto)]
        private static extern void SetDllDirectory(string lpPathName);


        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {


            /*
           * Make sure this path contains the umundoNativeCSharp.dll!
           */
            if (System.Environment.Is64BitProcess)
            {
                //SetDllDirectory("C:\\Program Files (x86)\\uMundo\\share\\umundo\\bindings\\csharp64");
                SetDllDirectory("C:\\Program Files\\uMundo\\share\\umundo\\bindings\\csharp64");
            }
            else
            {
                //SetDllDirectory("C:\\Program Files (x86)\\uMundo\\share\\umundo\\bindings\\csharp");
                SetDllDirectory("C:\\Program Files\\uMundo\\share\\umundo\\bindings\\csharp");
            }

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Newuser());
        }
    }
}
