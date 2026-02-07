package org.example;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
- subscribeOn: chạy đoạn code ở thread nào
- observerOn: khi có kết quả thì chạy
- Observable.zip thì giống zip trong flow kotlin: ghép cặp từng phần tử của 2 observable
- Observable.concat: ghép các observable với nhau nhưng chạy theo thứ tự khai báo, chỉ cái trước xong thì cái sau mới được chạy
- Observable.merge thì chạy các observable bên trong song song, cái nào xong trước thì emit trước
- Các cách tạo Observable:
+ .just() thì emit nguyên data đó (kiểu dữ liệu là gì thì emit kiểu dữ liệu đó)
+ .create() thì tự điều phối luồng emit theo ý mình
+ .fromArray thì emit từng phần tử trong mảng
+ .fromIterable thì emit từng phần tử trong iterable
+ .fromCallable thì trả về emit cái data trả về khi thực hiện xong logic code trong đó (hay dùng cho call api)
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Observable.concat(observable1(), observable2(), observable3())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        if (o instanceof Integer) {
                            System.out.println(Thread.currentThread().getName());
                            System.out.println(o);
                        } else if (o instanceof List) {
                            System.out.println(o);
                        } else if (o instanceof String) {
                            System.out.println(o);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        Thread.sleep(5000);
    }

    public static @NonNull Observable<ArrayList<Object>> observable1() throws InterruptedException {
        var list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        return Observable.just(list).delay(3, TimeUnit.SECONDS);
    }

    public static Observable<Integer> observable2() {
        return Observable.just(4, 5, 6);
    }

    public static Observable<String> observable3() {
        return Observable.fromCallable(() -> {
            System.out.println(Thread.currentThread().getName());
            return "hehehhe";
        });
    }

}