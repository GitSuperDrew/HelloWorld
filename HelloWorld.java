public HelloWorld{
  public static void main(String [] args){
    System.out.print("Hello World!");
    System.out.println("Nice to meet you!");
		Person person = new Person(1,Drew);
		person.toString();
  }
}
class Person{
	private int id;
	private string name;
	public Hello(){   //无参构造方法
		super();
	}
	public Hello(int id,String name){  //有参构造方法
		super();
		this.id = id;
		this.name = name;
	}
	//getter和setter方法
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	//toString 方法：
	public String toString(){
		System.out.println("编号："+id+",姓名："+name);
	}
}
