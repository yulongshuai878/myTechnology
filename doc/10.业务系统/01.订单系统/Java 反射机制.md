# Java 反射机制

Java的反射（reflection）机制是指在程序的运行状态中，可以改造任意一个类的对象，可以了解任意一个对象所属的类，可以了解任意一个类的成员变量和方法，可以调用任意一个对象的属性和方法。这种动态获取程序信息以及动态调用对象的功能成为Java语言的反射机制。反射被视为动态语言的关键。

## 1、功能

在运行时判断任意一个对象所属的类；在运行时构造任意一个类的对象；在运行时判断任意一个类所具有的成员变量和方法；在运行时调用任意一个对象的方法；生成动态代理。

有时候我们说某个语言具有很强的动态性，有时候我们会区分动态和静态的不同技术与做法。我们郎朗伤口动态绑定（dynamic binding）、动态链接（dynamic linking）、动态加载（dynamic loading）等。然而“动态”一次其实没有绝对而普遍使用的严格定义，有时候甚至像面向对象当初被导入编程领域一样，一人一把号，各吹各的调。

一般而言，开发者社群说到动态语言，大致认为的一个定义是：程序运行时，允许改变程序结构或变量类型，这种语言成为动态语言。从这个观点看，Perl、Python、Ruby是动态语言，C++、Java、C#不是动态语言。

尽管在这样的定义与分类下Java不是动态语言，它却有着一个非常突出的动态相关机制：Reflection。这个字的意思是“反射、映像、倒影”，用在Java身上指的是我们可以于运行时加载、探知、使用编译期间完全未知的classes。换句话说，Java程序可以加载一个运行时才得知名称的class，熟悉其完成构造（但不包括methods定义），并生成器对象实体、或对其fields设值、或唤起其methods。这种“看透class”的能力（the ability of the program to examine itself）被称为introspection（内省、内观、反省）。Reflection和introspection是常被并提的两个术语。

## 2、Class类

对于一个字节码文件.class，虽然表面上我们对该字节码文件一无所知，但该文件本身却记录了很多信息。Java在将.class字节码文件载入时，JVM将产生一个java.lang.Class对象代表该.class字节码文件，从该Class对象中可以获得类的许多基础信息，这就是反射机制。所以要想完成反射操作，就必须首先认识Class类。

反射机制所需的类主要有java.lang包中的Class类和java.lang.reflect包中的Constructor类、Field类、Method类和Parameter类。Class类是一个比较特殊的类，它是反射机制的基础，Class类的对象标识正在运行的Java程序中的类或接口，也就是任何一个类被加载时，即将类的.class文件（字节码文件）读入内存的同时，都自动为之创建一个java.lang.Class对象。Class类没有公共构造方法，其对象是JVM在加载时通过调用类加载器中的defineClass()方法创建的，因此不能显式地创建一个Class对象。通过这个Class对象，才可以获得该对象的其他信息。

| 常用方法                                                     | 功能说明                                            |
| ------------------------------------------------------------ | --------------------------------------------------- |
| public Package getPackage() {     return Package.getPackage(this); } | 返回Class对象所对应类的存放路径                     |
| public static Class<?> forName(String className)             | 返回名称为classNamed的类或接口的Class对象           |
| public String getName()                                      | 返回Class对象所对应类的“包.类名”形式的全名          |
| public native Class<? super T> getSuperclass();              | 返回Class对象所对应类的父类的Class对象              |
| public Class<?>[] getInterfaces()                            | 返回Class对象所对应类所实现的所有接口               |
| public Annotation[] getAnnotations()                         | 以数组的形式返回该程序元素上的所有注解              |
| public Constructor<T> getConstructor(Class<?>... parameterTypes) | 返回Class对象所对应类的指定参数列表的public构造函数 |
| public Constructor<?>[] getConstructors()                    | 返回Class对象所对应类的所有public构造方法           |

说明：通过getFields()和getMethods()方法获得权限为public成员变量和成员方法时，还包括从父类集成得到的成员变量和成员方法；而通过getDeclaredFields()和getDeclaredMethods()方法只获得在本类中定义的所有成员变量和成员方法。

每个类被加载之后，系统都会该类生成一个对象的Class对象，通过Class对象就可以访问到JVM中该类的信息，一旦类被加载到JVM中，同一个类将不会被再次载入。被载入JVM的类都有一个唯一标识就是该类的全名，即包括包名和类名。在Java中该程序获得Class对象有如下3中方式：

1. 使用Class类的静态方法forName(String className)，其中参数className表示所需类的全名。
2. 用类名调用该类的class属性来获得该类对应的Class对象，即“类名.class”。
3. 用对象调用getClass()方法来获得该类对象的Class对象

## 3、反射包reflect中的常用类

反射至中除了上面的Class类之外，还需要java.lang.reflect包中的Constructor类、Method类、Field类和Parameter类。Java 8以后在java.lang.reflect包中新增了一个Executable抽象类，该类对象代表可执行的类成员。Executable抽象类派生了Constructor和Method两个子类。

java.lang.reflect.Executable类提供了大量放用来获取参数、修饰符或注解等信息，其常用方法如下表所示：

| 常用方法                                       | 功能说明                                                     |
| ---------------------------------------------- | ------------------------------------------------------------ |
| public Parameter[] getParameters()             | 返回所有形参，存入数组Parameter[]中                          |
| public int getParameterCount()                 | 返回参数的个数                                               |
| public abstract Class<?>[] getParameterTypes() | 按声明顺序以Class数组的形式返回各参数的类型                  |
| public abstract int getModifiers()             | 返回整数标识的修饰符public、protected、private、final、static、abstract等关键字锁对应的常量 |
| public boolean isVarArgs()                     | 判断是否包含数量可变的参数                                   |

