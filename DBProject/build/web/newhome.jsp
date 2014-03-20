    <%-- 
        Document   : newhome
        Created on : 8 Nov, 2012, 3:21:41 AM
        Author     : Vaio
    --%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% HttpSession s = request.getSession();
String employee_type= (String)s.getAttribute("pos");
String emp_id = (String)s.getAttribute("emp_id");
String emp_name = (String)s.getAttribute("emp_name");
String emp_post = (String)s.getAttribute("pos");
if(employee_type.equals("CEO")){ %>
    <!--    CEO code here -->
    <html>
    <head>
            <title>Project Management Portal- Home</title>
            <script src="http://localhost/dbproject/js/jquery-1.6.2.min.js"></script>
            <script src="http://localhost/dbproject/js/jquery-ui-1.8.16.custom.min.js"></script>
            <link rel="stylesheet" href="http://localhost/dbproject/css/jquery-ui.css" />
            <link rel="stylesheet" href="http://localhost/dbproject/css/style.css" />
            <style>
                    .left{
                            width:25%;
                            padding:20px;
                            margin:0px;
                            text-align: center;
                            float:left;
                    }
                    .right{
                            width:66%;
                            padding:20px;
                            margin:10px;
                            float:left;
                            border:2px solid #a1a1a1;
                    }
                    #navigation{
                            margin:0px;
                            text-align:left;
                    }
                    a:link {text-decoration: none;}
                    a:visited {text-decoration: none;}
                    a:active {text-decoration: none;}
                    #navigation a:hover {color: blue;}
                    .hotlinks a:link {text-decoration: none;}
                    .hotlinks a:visited {text-decoration: none;}
                    .hotlinks a:active {text-decoration: none;}
                    .hotlinks a:hover {color: blue;}
                    #navigation h3 {font-weight:bold;}
                    .ui-accordion .ui-accordion-header {
                            display: block;
                            cursor: pointer;
                            position: relative;
                            margin-top: 0px;
                            padding: .1em .1em .1em 1em;
                            zoom: 1;
                    }
                    .ui-accordion .ui-accordion-content {
                            padding: 0.2em 2.2em;
                            border-top: 0;
                            overflow: auto;
                            zoom: 1;
                    }
                    .miniprofile{
                            margin-top:10px;
                            margin-bottom:20px;
                            height: 250px;
                    }
                    .para{
                            font-family: Georgia;
                            font-size:20px;
                            font-weight: bold;
                    }
                    .ui-widget-header {
                            border: 1px solid #AAA/*{borderColorHeader}*/;
                            background: #088A08/*{bgColorHeader}*/ url(http://localhost/dbproject/images/ui-bg_highlight-soft_75_cccccc_1x100.png)/*{bgImgUrlHeader}*/ 50%/*{bgHeaderXPos}*/ 50%/*{bgHeaderYPos}*/ repeat-x/*{bgHeaderRepeat}*/;
                            color: #222/*{fcHeader}*/;
                            font-weight: bold;
                    }
                    .projectdetails{
                            margin:10px;
                    }
            </style>
        <script>

                $(function() {
                    $( "#navigation" ).accordion();
                });

    //-------------------------------------------------------VARIABLE DECLARATIONS -------------------------------
                    var num_projects=10;


    //------------------------------------------------------------------------------------------------------------
                function display_projects(){
                    var status=new Array();
                    var ids=new Array();
                    var titles=new Array();
                    $.ajax({type:"POST",url:"GetProject",  data:"project_id="+"-99", success:function(data) {
                            //do nothing	
                                var arr=data.split(";;;");
                                var ctr=0;
                                for(x=0;x<arr.length;x++){
                                    var arr2=arr[x].split(";");
                                    if (""==arr2[1])continue;
                                    ids[ctr]=arr2[0];
                                    titles[ctr]=arr2[1];
                                    status[ctr]=arr2[5];
                                    ctr++;
                                }
                                for(var x=0;x<status.length;x++){
                                        document.getElementById("activity").innerHTML+="<a href='#createform' class='hotlinks' rel='facebox' onclick='startedit("+ids[x]+")'><div class='projectdetails' title='"+titles[x]+"'><span class='para'>Project "+String(x+1)+": "+status[x]+"% </span><div id='progress"+x+"' style='float:right;width:70%'></div>";

                                            $( '#progress'+x ).progressbar({
                                                value: parseInt(status[x],10)
                                            });
                                        document.getElementById("activity").innerHTML+="</div></a>";
                                }
                                $('a[rel*=facebox]').facebox()
                            }
                    });
                }
	function display_inbox(){
                var from=new Array();
                var msgs=new Array();
                var time=new Array();
                var status=new Array();
                var ids=new Array();
                $.ajax({type:"POST", url:"getMessage",  data:"type="+"0", success:function(data) {
                        //do nothing
                            var arr=data.split(";;;");
                            var ctr=0;
                            for(var x=0;x<arr.length; x++){
                                var arr2=arr[x].split(";;");
                                alert(arr[x]);
                                from[ctr]=arr2[0];
                                msgs[ctr]=arr2[1];
                                time[ctr]=arr2[2];
                                status[ctr]=arr2[3];
                                ids[ctr]=arr2[4];
                                ctr++;
                            }
                            var code = "";
                            for(var x=0;x<from.length;x++){
                                    var type = "old";
                                    if(status[x] == '1')
                                        type = "newest";
                                    code+="<tr><div class='"+type+"' ><td>"+from[x]+"</td><td>"+time[x]+" </td><td>"+msgs[x]+"</td><td><div id='create' style='float:right'><form method='post' action='deleteMessage' onsubmit='deleteMessage'><input type='hidden' name='id' value='"+ids[x]+"' /> <input type='submit' value='Delete'/></form></div></td></div></tr>";
                            }
                            document.getElementById("inbox").innerHTML="<div><table width=100%><tr><td>From:</td><td>TIme:</td><td>Message:</td></tr>"+code+"</table></div>";
                        }
                });
                document.getElementById('projects').style.display="none";
                document.getElementById('outMsg').style.display="none";
                document.getElementById('inMsg').style.display="block";
	    }

        function display_sent(){
                var to=new Array();
                var msgs=new Array();
                var time=new Array();
                var status=new Array();
                var ids=new Array();
                $.ajax({type:"POST", url:"getMessage",  data:"type="+"1", success:function(data) {
                        //do nothing
                            var arr=data.split(";;;");
                            var ctr=0;
                            for(var x=0;x<arr.length; x++){
                                var arr2=arr[x].split(";;");
                                to[ctr]=arr2[0];
                                msgs[ctr]=arr2[1];
                                time[ctr]=arr2[2];
                                status[ctr]=arr2[3];
                                ids[ctr]=arr2[4];
                                ctr++;
                            }
                            var code = "";
                            for(var x=0;x<to.length;x++){
                                    var type = "old";
                                    code+="<tr><div class='"+type+"'><td>"+to[x]+"</td><td>"+time[x]+" </td><td>"+msgs[x]+"</td><td><div id='create' style='float:right'><form method='post' action='deleteMessage' onsubmit='deleteMessage'><input type='hidden' name='id' value='"+ids[x]+"' /> <input type='submit' value='Delete'/></form></div></td></div></tr>";
                            }
                            document.getElementById("sent").innerHTML="<table width=100%><tr><td>To:</td><td>TIme:</td><td>Message:</td></tr>"+code+"</table>";
                        }
        });
        }

                function removeedit(){
                    document.getElementById("createh2").innerHTML="Create Project";
                    document.getElementById("project_id").value="";
                    document.getElementById("project_title").value="";
                    document.getElementById("budget").value="";
                    document.getElementById("deadline").value="";
                    document.getElementById("emp_id").value="";

                }
                function startedit(project_id){
                        document.getElementById("createh2").innerHTML="Edit Project";
                        var project_title,budget,deadline,emp_id;
                        $.ajax({type:"POST",url:"GetProject",  data:"project_id="+project_id, success:function(data) {
                            //do nothing	
                                var arr=data.split(";");
                                project_title=arr[1];
                                budget=arr[2];
                                deadline=arr[3];
                                emp_id=arr[4];
                                document.getElementById("project_id").value=project_id;
                                if(project_title!="null" && project_title!=undefined)document.getElementById("project_title").value=project_title;
                                if(budget!="null" && budget!=undefined)document.getElementById("budget").value=budget;
                                if(deadline!="null" && deadline!=undefined)document.getElementById("deadline").value=deadline;
                                if(emp_id!="null" && emp_id!=undefined && emp_id!=null)document.getElementById("emp_id").value=emp_id;
                            }
                         });

                }
        </script>

    <script type="text/javascript" src="http://localhost/dbproject/js/jquery.form.js"></script>
    <link href="http://localhost/dbproject/facebox/facebox.css" media="screen" rel="stylesheet" type="text/css"/>
    <script src="http://localhost/dbproject/facebox/facebox.js" type="text/javascript"></script> 
    <script type="text/javascript">
    $(document).ready(function(){
             $(".contimage").hover(function() {
                            $(this).animate({
                                    opacity:1
                            },200);
                    }, function() {
                            $(this).animate({
                                    opacity:0.8
                            },200);
                        });


             }); 
             display_projects();
    </script>
    <script type="text/javascript">

            $(document).ready(function() {
                    $('#submitform').ajaxForm({
                            target: '#error1',
                            success: function() {
                            document.getElementById('submitform').style.display="none";
                            $('#error1').fadeIn('slow');
                            }
                    });
            });

    </script>

    </head>
    <body style="background-color:#f3f3f3;">
            <div id="wrapper">
                    
<div class="left">
            <div class="miniprofile">
                <img src="http://localhost/dbproject/images/img<% out.print(emp_id); %>.jpg" height=60%;/>
                <h1 style="color:#A4A4A4;"><% out.print(emp_name); %></h1>
                <h1 style="color:#848484;"><% out.print(emp_post); %></h1>
            </div>
            <div id="navigation">
                <h3>Home</h3>
                <div>
                    <p>
                        <a href='newhome.jsp'>Projects</a>
                    </p>
                    <p>
                        <a href='LogOut'>Logout</a>
                    </p>
                </div>
                            <h3>Messages</h3>
                <div>
                    <p>
                                    <a href='#' onclick='display_inbox()'>Inbox</a>
                    </p>
                    <p>
                                    <a href='#' onclick='display_sent()'>Sent</a>
                    </p>
                </div>
                <h3>Profile</h3>
                <div>
                    <p>
                        <a href='EditProfile?action=2'>View/Edit Profile</a>
                    </p>
                    <p>
                        <a href='changePassword.jsp'>Change Your Password</a>
                    </p>
                </div>
                <h3>Help</h3>
                <div>
                    <p>
                        <a href='help.html'>FAQ</a>
                    </p>
                    <p>
                        <a href='contact_us.html'>Contact Us</a>
                    </p>
                </div>
            </div>
        </div>

                    <div class="right" id="projects">
                            <h1 style="color:#848484;">Projects</h1>
                            <div id="activity" class="activity" style="border:1px solid #a1a1a1;padding:10px">

                            </div>	
                            <div id="create"><a href="#createform" rel="facebox"><button type="button" />Create Project</button></a></div>

                    </div>
                        <div id="inMsg" class="right" style="display:none">
			<h1 style="color:#848484;">Messages</h1>
                        <h2 style="color:#848484;">Inbox</h2>
			<div id="inbox" class="inbox" style="border:1px solid #a1a1a1;padding:10px">
			</div>
                        <div id="create"><a href="#createform" rel="facebox"><button type="button" />Compose Message</button></a></div>
		</div>
                <div id="outMsg" class="right" style="display:none">
			<h1 style="color:#848484;">Messages</h1>
                        <h2 style="color:#848484;">Sent</h2>
			<div id="sent" class="sent" style="border:1px solid #a1a1a1;padding:10px">
			</div>
                        <div id="create"><a href="#createform" rel="facebox"><button type="button" />Compose Message</button></a></div>
		</div>
            </div>


    <div id="createform" style="display:none">
            <h2 id="createh2">Create Project</h2>
            <div id="createform">
            <div id="error1">
            </div>
            <div id="formleft">
                    <form id="submitform" action="Project" method="post">
                            <fieldset>
                                    <legend>Title</legend>
                                    <input type="text" name="project_title" id="project_title"  />
                            </fieldset>
                            <fieldset>
                                    <legend>Budget</legend>
                                    <input type="text" name="budget" id="budget" />
                            </fieldset>
                            <fieldset>
                                    <legend>Deadline <b>(yyyy-mm-dd)</b></legend>
                                    <input type="text" name="deadline" id="deadline"/>
                            </fieldset>
                            <fieldset>
                                    <legend>Assigned Employee ID</legend>
                                    <input type="text" name="emp_id" id="emp_id"/>
                            </fieldset>
                            <fieldset style="display:none"  id="project_id_display">
                                    <input type="text" name="project_id" id="project_id"/>
                            </fieldset>
                            <fieldset>
                                    <input type="submit" class="button" value="Submit" />
                            </fieldset>
                    </form>
                    </div>	
            <div class="clear"></div>
            </div>
    </div>

    </body>
    <script>//display_projects()</script>
    </html>



<% }else  if(employee_type.equals("Manager")){ %>
    <!--    Manager code here -->

    <html>
    <head>
            <title>Project Management Portal- Home</title>
            <script src="http://localhost/dbproject/js/jquery-1.6.2.min.js"></script>
            <script src="http://localhost/dbproject/js/jquery-ui-1.8.16.custom.min.js"></script>
            <link rel="stylesheet" href="http://localhost/dbproject/css/jquery-ui.css" />
            <link rel="stylesheet" href="http://localhost/dbproject/css/style.css" />
            <style>
                    .left{
                            width:25%;
                            padding:20px;
                            margin:0px;
                            text-align: center;
                            float:left;
                    }
                    .right{
                            width:66%;
                            padding:20px;
                            margin:10px;
                            float:left;
                            border:2px solid #a1a1a1;
                    }
                    #navigation{
                            margin:0px;
                            text-align:left;
                    }
                    a:link {text-decoration: none;}
                    a:visited {text-decoration: none;}
                    a:active {text-decoration: none;}
                    #navigation a:hover {color: blue;}
                    .hotlinks a:link {text-decoration: none;}
                    .hotlinks a:visited {text-decoration: none;}
                    .hotlinks a:active {text-decoration: none;}
                    .hotlinks a:hover {color: blue;}
                    #navigation h3 {font-weight:bold;}
                    .ui-accordion .ui-accordion-header {
                            display: block;
                            cursor: pointer;
                            position: relative;
                            margin-top: 0px;
                            padding: .1em .1em .1em 1em;
                            zoom: 1;
                    }
                    .ui-accordion .ui-accordion-content {
                            padding: 0.2em 2.2em;
                            border-top: 0;
                            overflow: auto;
                            zoom: 1;
                    }
                    .miniprofile{
                            margin-top:10px;
                            margin-bottom:20px;
                            height: 250px;
                    }
                    .para{
                            font-family: Georgia;
                            font-size:20px;
                            font-weight: bold;
                    }
                    .ui-widget-header {
                            border: 1px solid #AAA/*{borderColorHeader}*/;
                            background: #088A08/*{bgColorHeader}*/ url(http://localhost/dbproject/images/ui-bg_highlight-soft_75_cccccc_1x100.png)/*{bgImgUrlHeader}*/ 50%/*{bgHeaderXPos}*/ 50%/*{bgHeaderYPos}*/ repeat-x/*{bgHeaderRepeat}*/;
                            color: #222/*{fcHeader}*/;
                            font-weight: bold;
                    }
                    .projectdetails{
                            margin:10px;
                    }
            </style>
        <script>

                $(function() {
                    $( "#navigation" ).accordion();
                });

    //-------------------------------------------------------VARIABLE DECLARATIONS -------------------------------
                    var num_projects=10;


    //------------------------------------------------------------------------------------------------------------
                function display_projects(){
                    var status=new Array();
                    var ids=new Array();
                    var titles=new Array();
                    $.ajax({type:"POST",url:"GetProject",  data:"project_id="+"-99", success:function(data) {
                            //do nothing	
                                var arr=data.split(";;;");
                                var ctr=0;
                                for(x=0;x<arr.length;x++){
                                    var arr2=arr[x].split(";");
                                    if(arr2.length==0)continue;
                                    ids[ctr]=arr2[0];
                                    titles[ctr]=arr2[1];
                                    status[ctr]=arr2[5];
                                    ctr++;
                                }
                                for(var x=0;x<status.length;x++){
                                        document.getElementById("activity").innerHTML+="<a href='show-tasks.jsp?project_id="+ids[x]+"&project_title="+titles[x]+"' class='hotlinks' ><div class='projectdetails' title='"+titles[x]+"'><span class='para'>Project "+String(x+1)+": "+status[x]+"% </span><div id='progress"+x+"' style='float:right;width:70%'></div>";

                                            $( '#progress'+x ).progressbar({
                                                value: parseInt(status[x],10)
                                            });
                                        document.getElementById("activity").innerHTML+="</div></a>";
                                }
                                $('a[rel*=facebox]').facebox()
                            }
                    });



                }

                function removeedit(){
                    document.getElementById("createh2").innerHTML="Create Project";
                    document.getElementById("project_id").value="";
                    document.getElementById("project_title").value="";
                    document.getElementById("budget").value="";
                    document.getElementById("deadline").value="";
                    document.getElementById("emp_id").value="";

                }
                function startedit(project_id){
                        document.getElementById("createh2").innerHTML="Edit Project";
                        var project_title,budget,deadline,emp_id;
                        $.ajax({type:"POST",url:"GetProject",  data:"project_id="+project_id, success:function(data) {
                            //do nothing	
                                var arr=data.split(";");
                                project_title=arr[1];
                                budget=arr[2];
                                deadline=arr[3];
                                emp_id=arr[4];
                                document.getElementById("project_id").value=project_id;
                                if(project_title!="null" && project_title!=undefined)document.getElementById("project_title").value=project_title;
                                if(budget!="null" && budget!=undefined)document.getElementById("budget").value=budget;
                                if(deadline!="null" && deadline!=undefined)document.getElementById("deadline").value=deadline;
                                if(emp_id!="null" && emp_id!=undefined && emp_id!=null)document.getElementById("emp_id").value=emp_id;
                            }
                         });

                }
        </script>

    <script type="text/javascript" src="http://localhost/dbproject/js/jquery.form.js"></script>
    <link href="http://localhost/dbproject/facebox/facebox.css" media="screen" rel="stylesheet" type="text/css"/>
    <script src="http://localhost/dbproject/facebox/facebox.js" type="text/javascript"></script> 
    <script type="text/javascript">
    $(document).ready(function(){
             $(".contimage").hover(function() {
                            $(this).animate({
                                    opacity:1
                            },200);
                    }, function() {
                            $(this).animate({
                                    opacity:0.8
                            },200);
                        });


             }); 
             display_projects();
    </script>
    <script type="text/javascript">

            $(document).ready(function() {
                    $('#submitform').ajaxForm({
                            target: '#error1',
                            success: function() {
                            document.getElementById('submitform').style.display="none";
                            $('#error1').fadeIn('slow');
                            }
                    });
            });

    </script>

    </head>
    <body style="background-color:#f3f3f3;">
            <div id="wrapper">
                    
<div class="left">
            <div class="miniprofile">
                <img src="http://localhost/dbproject/images/img<% out.print(emp_id); %>.jpg" height=60%;/>
                <h1 style="color:#A4A4A4;"><% out.print(emp_name); %></h1>
                <h1 style="color:#848484;"><% out.print(emp_post); %></h1>
            </div>
            <div id="navigation">
                <h3>Home</h3>
                <div>
                    <p>
                        <a href='newhome.jsp'>Projects</a>
                    </p>
                    <p>
                        <a href='LogOut'>Logout</a>
                    </p>
                </div>
                            <h3>Messages</h3>
                <div>
                    <p>
                                    <a href='#' onclick='display_inbox()'>Inbox</a>
                    </p>
                    <p>
                                    <a href='#' onclick='display_sent()'>Sent</a>
                    </p>
                </div>
                <h3>Profile</h3>
                <div>
                    <p>
                        <a href='EditProfile?action=2'>View/Edit Profile</a>
                    </p>
                    <p>
                        <a href='changePassword.jsp'>Change Your Password</a>
                    </p>
                </div>
                <h3>Help</h3>
                <div>
                    <p>
                        <a href='help.html'>FAQ</a>
                    </p>
                    <p>
                        <a href='contact_us.html'>Contact Us</a>
                    </p>
                </div>
            </div>
        </div>
<script>
	function display_inbox(){
                var from=new Array();
                var msgs=new Array();
                var time=new Array();
                var status=new Array();
                var ids=new Array();
                $.ajax({type:"POST", url:"getMessage",  data:"type="+"0", success:function(data) {
                        //do nothing
                            var arr=data.split(";;;");
                            var ctr=0;
                            for(var x=0;x<arr.length; x++){
                                var arr2=arr[x].split(";;");
                                alert(arr[x]);
                                from[ctr]=arr2[0];
                                msgs[ctr]=arr2[1];
                                time[ctr]=arr2[2];
                                status[ctr]=arr2[3];
                                ids[ctr]=arr2[4];
                                ctr++;
                            }
                            var code = "";
                            for(var x=0;x<from.length;x++){
                                    var type = "old";
                                    if(status[x] == '1')
                                        type = "newest";
                                    code+="<tr><div class='"+type+"' ><td>"+from[x]+"</td><td>"+time[x]+" </td><td>"+msgs[x]+"</td><td><div id='create' style='float:right'><form method='post' action='deleteMessage' onsubmit='deleteMessage'><input type='hidden' name='id' value='"+ids[x]+"' /> <input type='submit' value='Delete'/></form></div></td></div></tr>";
                            }
                            document.getElementById("inbox").innerHTML="<div><table width=100%><tr><td>From:</td><td>TIme:</td><td>Message:</td></tr>"+code+"</table></div>";
                        }
                });
                document.getElementById('projects').style.display="none";
                document.getElementById('outMsg').style.display="none";
                document.getElementById('inMsg').style.display="block";
	    }

        function display_sent(){
                var to=new Array();
                var msgs=new Array();
                var time=new Array();
                var status=new Array();
                var ids=new Array();
                $.ajax({type:"POST", url:"getMessage",  data:"type="+"1", success:function(data) {
                        //do nothing
                            var arr=data.split(";;;");
                            var ctr=0;
                            for(var x=0;x<arr.length; x++){
                                var arr2=arr[x].split(";;");
                                to[ctr]=arr2[0];
                                msgs[ctr]=arr2[1];
                                time[ctr]=arr2[2];
                                status[ctr]=arr2[3];
                                ids[ctr]=arr2[4];
                                ctr++;
                            }
                            var code = "";
                            for(var x=0;x<to.length;x++){
                                    var type = "old";
                                    code+="<tr><div class='"+type+"'><td>"+to[x]+"</td><td>"+time[x]+" </td><td>"+msgs[x]+"</td><td><div id='create' style='float:right'><form method='post' action='deleteMessage' onsubmit='deleteMessage'><input type='hidden' name='id' value='"+ids[x]+"' /> <input type='submit' value='Delete'/></form></div></td></div></tr>";
                            }
                            document.getElementById("sent").innerHTML="<table width=100%><tr><td>To:</td><td>TIme:</td><td>Message:</td></tr>"+code+"</table>";
                        }
        });}
</script>

                    <div class="right" id="projects">
                            <h1 style="color:#848484;">Projects</h1>
                            <div id="activity" class="activity" style="border:1px solid #a1a1a1;padding:10px">

                            </div>	
                    </div>
                        <div id="inMsg" class="right" style="display:none">
			<h1 style="color:#848484;">Messages</h1>
                        <h2 style="color:#848484;">Inbox</h2>
			<div id="inbox" class="inbox" style="border:1px solid #a1a1a1;padding:10px">
			</div>
                        <div id="create"><a href="#createform" rel="facebox"><button type="button" />Compose Message</button></a></div>
		</div>
                <div id="outMsg" class="right" style="display:none">
			<h1 style="color:#848484;">Messages</h1>
                        <h2 style="color:#848484;">Sent</h2>
			<div id="sent" class="sent" style="border:1px solid #a1a1a1;padding:10px">
			</div>
                        <div id="create"><a href="#createform" rel="facebox"><button type="button" />Compose Message</button></a></div>
		</div>
            </div>


    <div id="createform" style="display:none">
            <h2 id="createh2">Create Project</h2>
            <div id="createform">
            <div id="error1">
            </div>
            <div id="formleft">
                    <form id="submitform" action="Project" method="post">
                            <fieldset>
                                    <legend>Title</legend>
                                    <input type="text" name="project_title" id="project_title"  />
                            </fieldset>
                            <fieldset>
                                    <legend>Budget</legend>
                                    <input type="text" name="budget" id="budget" />
                            </fieldset>
                            <fieldset>
                                    <legend>Deadline <b>(yyyy-mm-dd)</b></legend>
                                    <input type="text" name="deadline" id="deadline"/>
                            </fieldset>
                            <fieldset>
                                    <legend>Assigned Employee ID</legend>
                                    <input type="text" name="emp_id" id="emp_id"/>
                            </fieldset>
                            <fieldset style="display:none"  id="project_id_display">
                                    <input type="text" name="project_id" id="project_id"/>
                            </fieldset>
                            <fieldset>
                                    <input type="submit" class="button" value="Submit" />
                            </fieldset>
                    </form>
                    </div>	
            <div class="clear"></div>
            </div>
    </div>

    </body>
    <script>//display_projects()</script>
    </html>

<% }else  if(employee_type.equals("Engineer")){ %>  
    <!--    Employee code here -->
    <html>
    <head>
            <title>Project Management Portal- Home</title>
            <script src="http://localhost/dbproject/js/jquery-1.6.2.min.js"></script>
            <script src="http://localhost/dbproject/js/jquery-ui-1.8.16.custom.min.js"></script>
            <link rel="stylesheet" href="http://localhost/dbproject/css/jquery-ui.css" />
            <link rel="stylesheet" href="http://localhost/dbproject/css/style.css" />
            <style>
                    .left{
                            width:25%;
                            padding:20px;
                            margin:0px;
                            text-align: center;
                            float:left;
                    }
                    .right{
                            width:66%;
                            padding:20px;
                            margin:10px;
                            float:left;
                            border:2px solid #a1a1a1;
                    }
                    #navigation{
                            margin:0px;
                            text-align:left;
                    }
                    a:link {text-decoration: none;}
                    a:visited {text-decoration: none;}
                    a:active {text-decoration: none;}
                    #navigation a:hover {color: blue;}
                    .hotlinks a:link {text-decoration: none;}
                    .hotlinks a:visited {text-decoration: none;}
                    .hotlinks a:active {text-decoration: none;}
                    .hotlinks a:hover {color: blue;}
                    #navigation h3 {font-weight:bold;}
                    .ui-accordion .ui-accordion-header {
                            display: block;
                            cursor: pointer;
                            position: relative;
                            margin-top: 0px;
                            padding: .1em .1em .1em 1em;
                            zoom: 1;
                    }
                    .ui-accordion .ui-accordion-content {
                            padding: 0.2em 2.2em;
                            border-top: 0;
                            overflow: auto;
                            zoom: 1;
                    }
                    .miniprofile{
                            margin-top:10px;
                            margin-bottom:20px;
                            height: 250px;
                    }
                    .para{
                            font-family: Georgia;
                            font-size:20px;
                            font-weight: bold;
                    }
                    .ui-widget-header {
                            border: 1px solid #AAA/*{borderColorHeader}*/;
                            background: #088A08/*{bgColorHeader}*/ url(http://localhost/dbproject/images/ui-bg_highlight-soft_75_cccccc_1x100.png)/*{bgImgUrlHeader}*/ 50%/*{bgHeaderXPos}*/ 50%/*{bgHeaderYPos}*/ repeat-x/*{bgHeaderRepeat}*/;
                            color: #222/*{fcHeader}*/;
                            font-weight: bold;
                    }
                    .projectdetails{
                            margin:10px;
                            margin-top:30px;
                    }
            </style>
        <script>

                $(function() {
                    $( "#navigation" ).accordion();
                });

    //-------------------------------------------------------VARIABLE DECLARATIONS -------------------------------
                    var num_projects=10;


    //------------------------------------------------------------------------------------------------------------
                function display_projects(){
                    var status=new Array();
                    var ids=new Array();
                    var deadlines=new Array();
                    var titles=new Array();
                    $.ajax({type:"POST",url:"GetTaskEngg",  data:"task_id="+"-99", success:function(data) {
                            //do nothing
                                var arr=data.split(";");
                                var ctr=0;
                                for(var x=3;x<arr.length;x += 4){
                                    ids[ctr]=arr[x-3];
                                    titles[ctr]=arr[x-2];
                                    deadlines[ctr]=arr[x-1];
                                    status[ctr]=arr[x];
                                    ctr++;
                                }
                                for(var x=0;x<status.length;x++){
                                        var submit = "";
                                        var erase = "";

                                        if(status[x] == 'c')
                                            submit = "disabled='disabled' ";
                                        else
                                            erase = "disabled='disabled' ";
                                        document.getElementById("activity").innerHTML+="<div class='projectdetails' title='"+titles[x]+"'><span class='para'>Task "+ids[x]+": "+titles[x]+" ("+deadlines[x]+")</span><div id='create' style='float:right'><form method='post' action='markComplete' onsubmit='markComplete'><input type='hidden' name='id' value='"+ids[x]+"' /><input type='hidden' name='type' value='0' />     <input type='submit' value='Mark Complete' "+submit+"/><input type='submit' formaction='deleteTask' value='Delete' "+erase+"/></form></span></div>";
                                }
                            }
                    });
                }

                function removeedit(){
                    document.getElementById("createh2").innerHTML="Create Project";
                    document.getElementById("project_id").value="";
                    document.getElementById("project_title").value="";
                    document.getElementById("budget").value="";
                    document.getElementById("deadline").value="";
                    document.getElementById("emp_id").value="";

                }
                function startedit(project_id){
                        document.getElementById("createh2").innerHTML="Edit Project";
                        var project_title,budget,deadline,emp_id;
                        $.ajax({type:"POST",url:"GetProject",  data:"project_id="+project_id, success:function(data) {
                            //do nothing
                                var arr=data.split(";");
                                project_title=arr[1];
                                budget=arr[2];
                                deadline=arr[3];
                                emp_id=arr[4];
                                document.getElementById("project_id").value=project_id;
                                if(project_title!="null" && project_title!=undefined)document.getElementById("project_title").value=project_title;
                                if(budget!="null" && budget!=undefined)document.getElementById("budget").value=budget;
                                if(deadline!="null" && deadline!=undefined)document.getElementById("deadline").value=deadline;
                                if(emp_id!="null" && emp_id!=undefined && emp_id!=null)document.getElementById("emp_id").value=emp_id;
                            }
                         });

                }
        </script>

    <script type="text/javascript" src="http://localhost/dbproject/js/jquery.form.js"></script>
    <link href="http://localhost/dbproject/facebox/facebox.css" media="screen" rel="stylesheet" type="text/css"/>
    <script src="http://localhost/dbproject/facebox/facebox.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function(){
             $(".contimage").hover(function() {
                            $(this).animate({
                                    opacity:1
                            },200);
                    }, function() {
                            $(this).animate({
                                    opacity:0.8
                            },200);
                        });


             });
             display_projects();
    </script>
    <script type="text/javascript">

            $(document).ready(function() {
                    $('#submitform').ajaxForm({
                            target: '#error1',
                            success: function() {
                            document.getElementById('submitform').style.display="none";
                            $('#error1').fadeIn('slow');
                            }
                    });
            });

    </script>

    </head>

    <%@page import="javax.servlet.http.HttpSession" %>
           

    <body style="background-color:#f3f3f3;">
            <div id="wrapper">
                    
<div class="left">
            <div class="miniprofile">
                <img src="http://localhost/dbproject/images/img<% out.print(emp_id); %>.jpg" height=60%;/>
                <h1 style="color:#A4A4A4;"><% out.print(emp_name); %></h1>
                <h1 style="color:#848484;"><% out.print(emp_post); %></h1>
            </div>
            <div id="navigation">
                <h3>Home</h3>
                <div>
                    <p>
                        <a href='newhome.jsp'>Projects</a>
                    </p>
                    <p>
                        <a href='LogOut'>Logout</a>
                    </p>
                </div>
                            <h3>Messages</h3>
                <div>
                    <p>
                                    <a href='#' onclick='display_inbox()'>Inbox</a>
                    </p>
                    <p>
                                    <a href='#' onclick='display_sent()'>Sent</a>
                    </p>
                </div>
                <h3>Profile</h3>
                <div>
                    <p>
                        <a href='EditProfile?action=2'>View/Edit Profile</a>
                    </p>
                    <p>
                        <a href='changePassword.jsp'>Change Your Password</a>
                    </p>
                </div>
                <h3>Help</h3>
                <div>
                    <p>
                        <a href='help.html'>FAQ</a>
                    </p>
                    <p>
                        <a href='contact_us.html'>Contact Us</a>
                    </p>
                </div>
            </div>
        </div>
<script>
	function display_inbox(){
                var from=new Array();
                var msgs=new Array();
                var time=new Array();
                var status=new Array();
                var ids=new Array();
                $.ajax({type:"POST", url:"getMessage",  data:"type="+"0", success:function(data) {
                        //do nothing
                            var arr=data.split(";;;");
                            var ctr=0;
                            for(var x=0;x<arr.length; x++){
                                var arr2=arr[x].split(";;");
                                alert(arr[x]);
                                from[ctr]=arr2[0];
                                msgs[ctr]=arr2[1];
                                time[ctr]=arr2[2];
                                status[ctr]=arr2[3];
                                ids[ctr]=arr2[4];
                                ctr++;
                            }
                            var code = "";
                            for(var x=0;x<from.length;x++){
                                    var type = "old";
                                    if(status[x] == '1')
                                        type = "newest";
                                    code+="<tr><div class='"+type+"' ><td>"+from[x]+"</td><td>"+time[x]+" </td><td>"+msgs[x]+"</td><td><div id='create' style='float:right'><form method='post' action='deleteMessage' onsubmit='deleteMessage'><input type='hidden' name='id' value='"+ids[x]+"' /> <input type='submit' value='Delete'/></form></div></td></div></tr>";
                            }
                            document.getElementById("inbox").innerHTML="<div><table width=100%><tr><td>From:</td><td>TIme:</td><td>Message:</td></tr>"+code+"</table></div>";
                        }
                });
                document.getElementById('projects').style.display="none";
                document.getElementById('outMsg').style.display="none";
                document.getElementById('inMsg').style.display="block";
	    }

        function display_sent(){
                var to=new Array();
                var msgs=new Array();
                var time=new Array();
                var status=new Array();
                var ids=new Array();
                $.ajax({type:"POST", url:"getMessage",  data:"type="+"1", success:function(data) {
                        //do nothing
                            var arr=data.split(";;;");
                            var ctr=0;
                            for(var x=0;x<arr.length; x++){
                                var arr2=arr[x].split(";;");
                                to[ctr]=arr2[0];
                                msgs[ctr]=arr2[1];
                                time[ctr]=arr2[2];
                                status[ctr]=arr2[3];
                                ids[ctr]=arr2[4];
                                ctr++;
                            }
                            var code = "";
                            for(var x=0;x<to.length;x++){
                                    var type = "old";
                                    code+="<tr><div class='"+type+"'><td>"+to[x]+"</td><td>"+time[x]+" </td><td>"+msgs[x]+"</td><td><div id='create' style='float:right'><form method='post' action='deleteMessage' onsubmit='deleteMessage'><input type='hidden' name='id' value='"+ids[x]+"' /> <input type='submit' value='Delete'/></form></div></td></div></tr>";
                            }
                            document.getElementById("sent").innerHTML="<table width=100%><tr><td>To:</td><td>TIme:</td><td>Message:</td></tr>"+code+"</table>";
                        }
        });}
</script>

                    <div class="right" id="projects">
                            <h1 style="color:#848484;">Projects</h1>
                            <div id="activity" class="activity" style="border:1px solid #a1a1a1;padding:10px">

                            </div>
                    </div>
                        <div id="inMsg" class="right" style="display:none">
			<h1 style="color:#848484;">Messages</h1>
                        <h2 style="color:#848484;">Inbox</h2>
			<div id="inbox" class="inbox" style="border:1px solid #a1a1a1;padding:10px">
			</div>
                        <div id="create"><a href="#createform" rel="facebox"><button type="button" />Compose Message</button></a></div>
		</div>
                <div id="outMsg" class="right" style="display:none">
			<h1 style="color:#848484;">Messages</h1>
                        <h2 style="color:#848484;">Sent</h2>
			<div id="sent" class="sent" style="border:1px solid #a1a1a1;padding:10px">
			</div>
                        <div id="create"><a href="#createform" rel="facebox"><button type="button" />Compose Message</button></a></div>
		</div>
            </div>


    <div id="createform" style="display:none">
            <h2 id="createh2">Create Project</h2>
            <div id="createform">
            <div id="error1">
            </div>
            <div id="formleft">
                    <form id="submitform" action="Project" method="post">
                            <fieldset>
                                    <legend>Title</legend>
                                    <input type="text" name="project_title" id="project_title"  />
                            </fieldset>
                            <fieldset>
                                    <legend>Budget</legend>
                                    <input type="text" name="budget" id="budget" />
                            </fieldset>
                            <fieldset>
                                    <legend>Deadline <b>(yyyy-mm-dd)</b></legend>
                                    <input type="text" name="deadline" id="deadline"/>
                            </fieldset>
                            <fieldset>
                                    <legend>Assigned Employee ID</legend>
                                    <input type="text" name="emp_id" id="emp_id"/>
                            </fieldset>
                            <fieldset style="display:none"  id="project_id_display">
                                    <input type="text" name="project_id" id="project_id"/>
                            </fieldset>
                            <fieldset>
                                    <input type="submit" class="button" value="Submit" />
                            </fieldset>
                    </form>
                    </div>
            <div class="clear"></div>
            </div>
    </div>

    </body>
    <script>//display_projects()</script>
    </html>


<% } else{%>
    <h1>Unauthorized Access detected</h1>
<%  }  %>