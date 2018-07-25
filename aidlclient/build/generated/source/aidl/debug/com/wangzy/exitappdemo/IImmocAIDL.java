/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/wangzy/github/TestProject/aidlclient/src/main/aidl/com/wangzy/exitappdemo/IImmocAIDL.aidl
 */
package com.wangzy.exitappdemo;
public interface IImmocAIDL extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.wangzy.exitappdemo.IImmocAIDL
{
private static final java.lang.String DESCRIPTOR = "com.wangzy.exitappdemo.IImmocAIDL";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.wangzy.exitappdemo.IImmocAIDL interface,
 * generating a proxy if needed.
 */
public static com.wangzy.exitappdemo.IImmocAIDL asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.wangzy.exitappdemo.IImmocAIDL))) {
return ((com.wangzy.exitappdemo.IImmocAIDL)iin);
}
return new com.wangzy.exitappdemo.IImmocAIDL.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_add:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _result = this.add(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getBook:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.wangzy.exitappdemo.domain.Book _result = this.getBook(_arg0);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.wangzy.exitappdemo.IImmocAIDL
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public int add(int num1, int num2) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(num1);
_data.writeInt(num2);
mRemote.transact(Stub.TRANSACTION_add, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.wangzy.exitappdemo.domain.Book getBook(java.lang.String name) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.wangzy.exitappdemo.domain.Book _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
mRemote.transact(Stub.TRANSACTION_getBook, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.wangzy.exitappdemo.domain.Book.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_add = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public int add(int num1, int num2) throws android.os.RemoteException;
public com.wangzy.exitappdemo.domain.Book getBook(java.lang.String name) throws android.os.RemoteException;
}
