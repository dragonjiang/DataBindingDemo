# DataBindingDemo
# 写在前面

2015年的Google IO大会上，Android团队发布了一个官方的数据绑定框架（Data Binding Library）。通过这个框架可以直接在layout布局文件中绑定数据，无需再写繁琐的findViewById，可以为我们省下大量的样板代码；更进一步的，可以直接通过更改数据来改变视图，把繁琐的事情交给Data Binding Library。

本文结合[DataBindingDemo](https://github.com/dragonjiang/DataBindingDemo)介绍怎么使用Data Binding Library。文中的例子可前往[DataBindingDemo](https://github.com/dragonjiang/DataBindingDemo)查看。

# 配置环境

Data Binding Library是一个support库，最低支持到Android 2.1(API level 7+)。
- 要使用data binding需要将Gradle升级到1.5.0-alpha1或者更新的版本，Android Studio升级到1.3或者更新的版本。

- 通过Android SDK Manager下载最新的Support repository
- 需要在module的build.gradle中配置允许data binding


```
android {
    ....
    dataBinding {
        enabled = true
    }
}
```

 *注意：如果你的app module中依赖了使用data binding的库，app的build.gralde中也要配置允许data binding*

# Data Binding入门
## 布局文件
data binding布局文件不再纯粹展示UI的布局，而是添加了UI需要用的数据。所以根节点变成`layout`标签，并且新增了一个节点`data`。`data`节点之后才是`view`节点。

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
   <data>
       <variable name="user" type="com.example.User"/>
   </data>
   <LinearLayout
       android:orientation="vertical"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
       <TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@{user.firstName}"/>
       <TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@{user.lastName}"/>
   </LinearLayout>
</layout>
```
`data`里面的`variable`标签用于描述在布局里可能用到的属性。
布局文件中的表达式用"@{}"语法。

## 数据对象
假设你有一个POJO（plain-old Java object）的User类：

```
public class User {
   public final String firstName;
   public final String lastName;
   public User(String firstName, String lastName) {
       this.firstName = firstName;
       this.lastName = lastName;
   }
}
```
或者声明一个JavaBeans类型的User类：

```
public class User {
   private final String firstName;
   private final String lastName;
   public User(String firstName, String lastName) {
       this.firstName = firstName;
       this.lastName = lastName;
   }
   public String getFirstName() {
       return this.firstName;
   }
   public String getLastName() {
       return this.lastName;
   }
}
```
这两个类对于data binding是没有区别的。上文`android:text="@{user.lastName}"`用到的lastName，data binding会读取POJO类的lastName,或者JavaBeans的getLastName()。

## 绑定数据

默认情况下，在写完布局文件之后，data binding会自动生成一个以Binding结尾的Binding类，例如布局文件的名称是`activity_main.xml`，生成的Binding类是ActivityMainBinding.java。对于布局文件里面声明的`variable`，Binding类会自动生成setter、getter。

```
@Override
protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
   User user = new User("Bai", "Li");
   binding.setUser(user);
}
```
DataBindingUtil提供了setContentView代替之前的setContentView方法，用于生成一个Binding类。也可用`inflate`方法：

```
ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
```
如果使用data binding的布局是用于`ListView`或者`RecycleView`的items，用法稍有不同：

```
ListItemBinding binding = ListItemBinding.inflate(layoutInflater, viewGroup, false);
//or
ListItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, viewGroup, false);

```

## 事件处理
除了绑定数据，Data Binding也可以响应视图的事件。有两种方式：
- 方法调用 ---- 在编译时处理
- 监听绑定 ---- 在事件触发时处理

### 方法调用
创建一个EventHandler类：
```
public class EventHandler {
    ActivityEventHandlingBinding mBinding;

    public EventHandler(ActivityEventHandlingBinding binding) {
        this.mBinding = binding;
    }

    /**
     * 方法调用
     *
     * @param view
     */
    public void onClick(View view) {
        mBinding.setUser(new User("bai", "li"));
    }
}
```
在布局文件的`data`节点中声明：

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="user"
            type="com.dragonjiang.databindingdemo.model.User" />

        <variable
            name="handler"
            type="com.dragonjiang.databindingdemo.handler.EventHandler" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{user.firstName + ` ` + user.lastName}" />

        <Button
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:onClick="@{handler::onClick}"
            android:text="method references" />
    </LinearLayout>
</layout>

```
*注意：`android:onClick`对应于java的`OnClickListener#onClick(View view)`，因此EventHandler类里面的onClick(View view)也要有相同的参数。*

### 监听绑定
其用法如下：

```
public class EventHandler {
    ActivityEventHandlingBinding mBinding;

    public EventHandler(ActivityEventHandlingBinding binding) {
        this.mBinding = binding;
    }

    /**
     * 事件绑定
     *
     * @param user
     */
    public void onClickEvent(User user) {
        User u = new User("pu", "du");
        mBinding.setUser(u);
    }
}
```

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        <variable
            name="user"
            type="com.dragonjiang.databindingdemo.model.User" />

        <variable
            name="handler"
            type="com.dragonjiang.databindingdemo.handler.EventHandler" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{user.firstName + ` ` + user.lastName}" />

        <Button
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:onClick="@{()->handler.onClickEvent(user)}"
            android:text="listener bindings" />
    </LinearLayout>
</layout>

```
如果你要在`onClickEvent`里面使用参数，可以采用如下写法：

```
<Button
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:onClick="@{(view)->handler.onClickEventWithParams(view, user)}"
    android:text="listener bindings with params" />
```
当然，参数`view`要嘛不写，要嘛有多个参数就要写全。参数名可以自己定义。例如：

```
<CheckBox
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:onCheckedChanged="@{(cb, isChecked) -> handler.onCheckBoxChanged(cb, isChecked)}" />
```

# Data Binding进阶--布局详情
上文已经介绍了Data Binding的基本使用，包括简单的数据绑定和事件绑定，接下来我们介绍Data Binding的进阶使用。
## 导入
在布局的`data`标签内可以声明多个`import`标签，`import`标签内`type`的值是类的全名。布局文件里面可以像Java一样使用import进来的类。

- 导入的类型用于表达式

```
<data>
    <import type="android.view.View"/>
</data>
```
然后你可以在View里面这样使用：

```
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@{user.lastName}"
    android:visibility="@{user.isAdult ? View.VISIBLE : View.GONE}" />
```
不用再在Java里面获取User的值，不用写findViewById，就可以直接根据数据的变化改变View的属性，是不是觉得很方便？

那如果我有两个类的类名相同，只是包名不同呢？或者我想用一个别名来代替类名呢？data binding帮我们想到了，这时候可以用别名`alias`:

```
<import
    alias="UserModel" type="com.dragonjiang.databindingdemo.model.User" />
    <!--这里使用UserModel代替com.dragonjiang.databindingdemo.model.User-->
    <variable name="user" type="UserModel" />
```

- 使用静态方法或属性

```
<data>
    <import type="com.example.MyStringUtils"/>
    <import alias="UserModel" type="com.dragonjiang.databindingdemo.model.User" />
    <variable name="user" type="UserModel" />
</data>
…
<TextView
   android:text="@{`static method: ` + StringUtil.capitalize(user.lastName)}"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"/>
```
*注意：与Java一样，`java.lang.*`是自动导入的。*

## 表达式语言

### 通用的特性
data binding支持的表达式语言有很多与Java表达式一样：

- Mathematical `+ - / * %`
- String concatenation `+`
- Logical `&& ||`
- Binary `& | ^`
- Unary `+ - ! ~`
- Shift `>> >>> <<`
- Comparison `== > < >= <=`
- `instanceof`
- Grouping `()`
- Literals - character, String, numeric, `null`
- Cast
- Method calls
- Field access
- Array access `[]`
- Ternary operator `?:`
例如：

```
android:text="@{String.valueOf(index + 1)}"
android:visibility="@{age < 13 ? View.GONE : View.VISIBLE}"
android:transitionName='@{"image_" + id}'

```

### 不支持的运算符
一些java的运算符data binding不支持：
- `this`
- `super`
- `new`
- 显式泛型调用 <T>

### Null合并运算符
data binding支持null合并运算符（??），在非null时选择左边，null时选择右边：

```
android:text="@{user.displayName ?? user.lastName}"
```
等价于：

```
android:text="@{user.displayName != null ? user.displayName : user.lastName}"
```

### 自动避免空指针异常
自动生成的data binding代码会自动检测空指针和避免空指针异常。例如在表达式`@{user.name}`中，如果user是null，user.name就会被赋值为null。如果引用的是user.age（int）,user.age就会被赋值为0。

### 集合类
通用的集合类：arrays,lists,sparse lists和maps,可以用`[]`操作符来操作。

```
<data>
        <import type="java.util.List" />
        <import type="android.util.SparseArray" />
        <import type="java.util.Map" />
        <import
            alias="UserModel"
            type="com.dragonjiang.databindingdemo.model.User" />
        <variable
            name="user"
            type="UserModel" />
        <variable
            name="userList"
            type="List&lt;UserModel&gt;" />
        <variable
            name="sparse"
            type="SparseArray&lt;String&gt;" />
        <variable
            name="map"
            type="Map&lt;String, String&gt;" />
        <variable
            name="key"
            type="String" />
        <variable
            name="index"
            type="int" />
    </data>
    ...
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
        
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{`userList: ` + userList[index].toString()}" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{`sparse: ` + sparse[index]}" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            <!--可以在""里面用``连接字符串，也可以用''-->
            android:text="@{`map: ` + map[key] + ' ' +map[`key2`]}" />
    </LinearLayout>
```

*注意`List<UserModel>`的`<`和`>`要进行转义：`List&lt;UserModel&gt;`*

### 资源
可以在表达式里面使用android资源：

```
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    <!--字符串格式化-->
    android:text="@{`stringFormat: ` + @string/nameFormat(user.firstName, user.lastName)}"
    android:textSize="@{large ? @dimen/large_text : @dimen/small_text}" />

<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    <!--使用复数-->
    android:text="@{`plural: `+@plurals/orange(index)}" />
```
在string.xml里面：

```
<resources>
    <string name="app_name">DataBindingDemo</string>
    <string name="nameFormat">fullname: %1$s %2$s</string>

    <plurals name="orange">
        <item quantity="one">Have an orange</item>
        <item quantity="other">Have %d oranges</item>
    </plurals>
</resources>
```
对于```android:text="@{`plural: `+@plurals/orange(index)}" />```,根据index的值，可以格式化为：`plural: Have an orange`或者`plural: Have %d oranges`。

在dimens.xml里面：

```
<resources>
    <dimen name="large_text">20sp</dimen>
    <dimen name="small_text">12sp</dimen>
</resources>
```

### Includes
在布局中可能用到`include`标签，`variable`可以传递到布局中使用的的任何`include`布局中：

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:bind="http://schemas.android.com/apk/res-auto">

    <data>

        <import
            alias="UserModel"
            type="com.dragonjiang.databindingdemo.model.User" />

        <variable
            name="user"
            type="UserModel" />

    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <include
            layout="@layout/include"
            bind:user="@{user}" />
    </LinearLayout>
</layout>
```
在include.xml中：

```
<?xml version="1.0" encoding="utf-8"?>
<layout>

    <data>
        <variable
            name="user"
            type="com.dragonjiang.databindingdemo.model.User" />
    </data>

    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:id="@+id/tvInclude"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{`include layout: ` + user.toString()}"
            android:textColor="@color/colorAccent" />
    </LinearLayout>
</layout>
```
*对比可以发现，在两个布局中都需要声明`user`variable。*

Data binding不支持包含`merge`标签的布局，举个***不能运行***的例子：

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:bind="http://schemas.android.com/apk/res-auto">
   <data>
       <variable name="user" type="com.example.User"/>
   </data>
   <!--data binding 不支持merge标签-->
   <merge>
       <include layout="@layout/name"
           bind:user="@{user}"/>
       <include layout="@layout/contact"
           bind:user="@{user}"/>
   </merge>
</layout>

```

# Data Binding 高级 -- 数据绑定
前面我们介绍了Data Binding的语法等进阶功能。接下来们来介绍一下Data Binding的数据对象。任何POJO对象都可以用在data binding中，但是对象改变时候，要如何通知UI更新呢？这是使用Data Binding最奥妙的地方。Ps：我们这边只是介绍如何使用，没有涉及到实现原理。

有三种不同的数据变化通知机制：`observable objects`, `observable fields`, and `observable collections`.

这些observable对象绑定到UI上，当对象的属性更改时就会自动通知UI更新。

## Observale Objects
一个继承`Observable`接口的类，data binding会设置一个listener用于监听绑定的对象的属性变化。

```
public interface Observable {

    /**
     * Adds a callback to listen for changes to the Observable.
     * @param callback The callback to start listening.
     */
    void addOnPropertyChangedCallback(OnPropertyChangedCallback callback);

    /**
     * Removes a callback from those listening for changes.
     * @param callback The callback that should stop listening.
     */
    void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback);

    /**
     * The callback that is called by Observable when an observable property has changed.
     */
    abstract class OnPropertyChangedCallback {

        /**
         * Called by an Observable whenever an observable property changes.
         * @param sender The Observable that is changing.
         * @param propertyId The BR identifier of the property that has changed. The getter
         *                   for this property should be annotated with {@link Bindable}.
         */
        public abstract void onPropertyChanged(Observable sender, int propertyId);
    }
}
```
`Observable`接口有注册/删除监听的方法，但是数据变化时是否通知取决于开发者。为了简化开发，data binding提供了一个`BaseObservable`的基类，帮我们实现了监听的注册和删除。这个类也实现了通知数据变化的方法，在`getter`中使用`Bindable`注解，在`setter`中调用`notifyPropertyChanged`通知数据变更。

```
public class ObservableUser extends BaseObservable {
    public String firstName;
    public String lastName;

    public ObservableUser(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }
}
```
`Bindable`注解会在编译时在BR中生成一个entry，当数据变化时调用`notifyPropertyChanged`通知这个entry数据发生了变化。

## ObservableFields
创建Observable类还是比较麻烦的，data binding为我们提供了一个便捷的`ObservableField`类以及它的派生类：
`
 ObservableBoolean, ObservableByte, ObservableChar, ObservableShort, ObservableInt, ObservableLong, ObservableFloat, ObservableDouble, and ObservableParcelable`. 

`ObservableFields`是包含了一个单一属性的observable objects，可以通过声明一个public final field来使用它：

```
public class ObservableFieldUser extends BaseObservable {
    public final ObservableField<String> firstName = new ObservableField<>();
    public final ObservableField<String> lastName = new ObservableField<>();

    public ObservableFieldUser(String firstName, String lastName) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
    }
}
```
然后可以用`set`/`get`来存取数据：

```
user.firstName.set("bai");
String s = user.firstName.get();
```

## Observable Collections
有些应用希望使用更加灵活的结构来管理数据，Observable集合类允许使用key来访问这些数据对象。
- 如果key是String，`ObservableArrayMap`会非常有用：

```
ObservableMap<String, String> userMap = new ObservableArrayMap<>();
userMap.put("firstName", "bai");
userMap.put("lastName", "li");
binding.setUserMap(userMap);
```
然后在布局文件中用String keys获取map中的数据：

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        <import type="android.databinding.ObservableMap" />
        <variable
            name="userMap"
            type="ObservableMap&lt;String,String&gt;" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{`obserable map : ` + userMap[`firstName`] + ` ` + userMap[`lastName`]}" />
    </LinearLayout>
</layout>
```

- 如果key是integer,`ObservableArrayList`会非常有用：

```
ObservableList<User> useList = new ObservableArrayList<>();
useList.add(new User("bai", "li"));
binding.setUserList(useList);
```
然后在布局文件中使用下标获取list中的数据：

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        <import type="com.dragonjiang.databindingdemo.model.User" />
        <import type="android.databinding.ObservableList" />
        <variable
            name="userList"
            type="ObservableList&lt;User&gt;" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{`obserable list : ` + userList[0].toString()}" />
    </LinearLayout>
</layout>

```

## 生成绑定
自动生成的Binding类都继承了`ViewDataBinding`类，它们是连接layout的variables和Views的桥梁。
### Creating
binding在View inflate之后创建。inflate方法会将Veiw绑定到binding上，对于不同的Veiw有不同的创建方法：

```
MyLayoutBinding binding = MyLayoutBinding.inflate(layoutInflater);
MyLayoutBinding binding = MyLayoutBinding.inflate(layoutInflater, viewGroup, false);
```
如果布局使用不同的机制inflate，可以单独绑定：

```
MyLayoutBinding binding = MyLayoutBinding.bind(viewRoot);

```
有时候绑定不能提前确定，例如ListView的Item layout，这时候可以使用`DataBindingUtil`类：

```
ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater, layoutId,
    parent, attachToParent);
ViewDataBinding binding = DataBindingUtil.bindTo(viewRoot, layoutId);

```

### 有ID的View
我们在之前的例子里面都没有给View声明一个id，因为用不到。但是如果有些情况下，我要调用到布局里面的特定的View，还是需要一个id。data binding提供了一个比findViewById更快的机制：

```
<TextView
    android:id="@+id/tv_name"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@{user.firstName + ` ` + user.lastName}" />
```
data binding会在binding类中自动生成对应的属性：
```
public final TextView tvName;
```
可以直接使用：

```
binding.tvName.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(LayoutDetailsActivity.this, binding.tvName.getText(), Toast.LENGTH_SHORT).show();
    }
});
```

### ViewStubs
`ViewStub`不同于正常的View，它一开始是不可见的，在需要时才加载出特定的布局。所以data binding提供了一个`ViewStubProxy`类来代替`ViewStub`,开发者可以通过这个类来操作`ViewStub`。

`ViewStub`需要在inflate时候创建一个binding，故需要设置监听`ViewStub.OnInflateLister`：

```
public class ViewStubActivity extends AppCompatActivity {

    private ActivityViewStubBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_stub);
        mBinding.viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                IncludeBinding binding = DataBindingUtil.bind(inflated);
                binding.setUser(new User("bai", "li"));
            }
        });
    }

    public void onClick(View view) {
        if (!mBinding.viewStub.isInflated()) {
            mBinding.viewStub.getViewStub().inflate();
        }
    }
}
```

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data></data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="onClick"
            android:text="inflate view_stub" />

        <ViewStub
            android:id="@+id/view_stub"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout="@layout/include" />
    </LinearLayout>
</layout>

```

### 高级绑定

有些情况下，例如`RecyclerView.Adapter`中我们无法事先知道binding类。需要在`onBindViewHodler(VH, int)`中给binding赋值。
在这种情况下，RecyclerView布局内都设置了一个`item`变量，可以通过`getBinding`方法返回一个`ViewDataBinding`类：

```
public void onBindViewHolder(VH holder, int position) {
    holder.binding.setModel(mDataList.get(position));
    holder.binding.executePendingBindings();
}
```
注意到上面`executePendingBindings()`表示立即绑定。如果没有指定立即执行，在数据变化时，binding会在下一帧开始前触发。

### 属性设置
当绑定的数据变化时，自动生成的binding类会寻找对应属性的setter方法。data binding框架设置了几种自定义赋值的机制。
- 自动Setter
对于一个属性，data binding 尝试找到对应的setter方法，例如我们自定义了一个`UserView`类，实现一个`setUser`方法：

```
public class UserView extends AppCompatTextView {
    public UserView(Context context) {
        super(context);
    }

    public UserView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUser(User user) {
        this.setText(user.toString());
    }
}
```
在布局文件中使用：

```
<com.dragonjiang.databindingdemo.ui.UserView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:user="@{user}" />
```
data binding自动为我们找到了`setUser(User user)`的方法。

- 重命名Setter
有的属性的名称与它的setter不匹配，对于这类属性，可以使用注解`BindingMethods`将属性与setter关联起来。例如下面这个例子将`andorid:tint`与`setImageTintList`关联起来：

```
@BindingMethods({
       @BindingMethod(type = "android.widget.ImageView",
                      attribute = "android:tint",
                      method = "setImageTintList"),
})
```

- 自定义Setter(Binding Adapter)
有些属性需要自定义属性设置逻辑，例如没有`android:paddingLeft`属性对应的setter方法。但是有`setPadding(left, top, right, bottom)`。一个用`BindingAdapter`注解的静态方法允许开发者自定义setter：

```
@android.databinding.BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, int padding) {
        view.setPadding(padding,
                view.getPaddingTop(),
                view.getPaddingRight(),
                view.getPaddingBottom());
    }
```

BindingAdapter的方法还可以获取旧的值。只需将旧的值放前面，新的值放后面：

```
@android.databinding.BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, int oldPadding, int padding) {
        if (oldPadding != padding) {
            view.setPadding(padding,
                    view.getPaddingTop(),
                    view.getPaddingRight(),
                    view.getPaddingBottom());
        }
    }
```

BindingAdapter很强大，尤其对自定义属性。比如可以用来异步加载图片：

```
@android.databinding.BindingAdapter({"imageUrl", "error"})
    public static void loadImage(ImageView view, String url, Drawable error) {
        Glide.with(view.getContext()).load(url).error(error).into(view);
    }
```

```
<ImageView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:error="@{@drawable/ic_launcher}"
    app:imageUrl="@{user.avatar}" />
```
当`imageUrl`和`error`属性被使用时，就会匹配调用BindindAdapter的`loadImage`方法。


## 转换器
### 对象转换
如果binding表达式返回一个对象，data binding会寻找对应的setter（自动setter、重命名setter、自定义setter），然后将返回的对象强制转换成setter需要的类型。
这是一个使用`ObservableMap`的例子：

```
<TextView
   android:text='@{userMap["lastName"]}'
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"/>
```
`userMap`返回一个对象，这个对象会被自动转换为`setText(CharSequence)`需要的类型。如果类型转换有问题，开发者需要受到进行类型转换。

### 自定义转换
有时候需要对一些特定的类型直接做转换，例如设置背景：

```
<com.dragonjiang.databindingdemo.ui.UserView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:background="@{user.isAdult ? @color/colorAccent : @color/colorPrimary}"
    app:user="@{user}" />
```
这里`background`需要`Drawable`类型，而color是int类型，此时需要一个BindingConversation将int转为ColorDrawable：

```
@BindingConversion
public static ColorDrawable convertColorToDrawable(int color) {
   return new ColorDrawable(color);
}
```
*注意：转换只能在setter时生效，所以++不允许++混合类型*：

```
<View
   android:background="@{isError ? @drawable/error : @color/white}"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"/>
```

# 参考资料
- [Data Binding Library](https://developer.android.com/topic/libraries/data-binding/index.html)
- [Android Data Binding 系列(一) -- 详细介绍与使用](http://connorlin.github.io/2016/07/02/Android-Data-Binding-%E7%B3%BB%E5%88%97-%E4%B8%80-%E8%AF%A6%E7%BB%86%E4%BB%8B%E7%BB%8D%E4%B8%8E%E4%BD%BF%E7%94%A8/)
- [完全掌握Android Data Binding](https://github.com/LyndonChin/MasteringAndroidDataBinding)





