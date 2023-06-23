VPC 2023
----


## Một số link

- [Đề thi](resources/VPC2023.pdf)
- [Code đáp án bằng C++ (không giải thích)](resources/Đáp án VPC 2023_C++_Judge.pdf)
- [Bảng ranking trước đóng băng](resources/ranking_before_freeze.csv)
- [Bảng ranking cuối cùng](resources/ranking_before_freeze.csv)
- [Toàn bộ input/output các bài](resources/TestcasesVPC2023.zip)
- [Link cuộc thi (có thể bị xoá bất kì lúc nào)](https://vpc.vnoi.info/contest/vpc)

## Các bài toán và lời giải

### A. Sắp xếp chỗ ngồi
Bài này đơn thuần là duyệt.

[View source (Java)](src/A_XepChoNgoi.java)

### B. Vận chuyển gạo

Sắp xếp vị trí của Nhà, và vị trí Kho gạo theo thứ tự tăng dần, rồi khi tới từng vị trí thì làm theo mô tả của đề bài.

[View source B_VanChuyenGao (Java)](src/B_VanChuyenGao.java)

### C. Khảo sát dân cư

Bài này cần sắp xếp theo thứ tự giảm dần của `f_i = c_i / p_i`.
Sau đó lấy lần lượt từ trên xuống dưới dừng lại khi `f_i < f_(i-1)`.
Bài sẽ hay hơn nếu yêu cầu in ra `k` lớn nhất, thay vì bất kì `k` nào.

[View source C_KhaoSatDanCu (Java)](src/C_KhaoSatDanCu.java)


### D. Kết nối

Đây là bài toán cây khung con nhỏ nhất, kết hợp với chiến lược tham lam để đặt các trạm phát sóng wifi trên cây khung này.

Kết quả cần trả ra là giá trị nhỏ nhất trong 2 trường hợp sau:
- Cost của việc nối cây khung cho n đỉnh, sử dụng hoàn toàn bằng cáp quang.
- Cost của việc dùng mix giữa trạm phát sóng và cáp quang. Cost này được tính bằng cách thay thế dần các đường cáp quang bằng trạm phát sóng wifi.

Chi tiết xem trong code:

[View source D_KetNoi (Java)](src/D_KetNoi.java)


### E. Nối vòng tay lớn

Bài này nếu chạy mô phỏng theo đúng yêu cầu đề bài sẽ bị quá thời gian (TLE). Do vậy cần tìm cách giảm `p` xuóng 1 giá trị `p'` rồi mới thực hiện mô phỏng.
Để làm được việc này,
- Trước tiên cần tìm minP - giá trị nhỏ nhất của p để trò chơi có thể diễn ra ít nhất tròn 1 vòng.
- Với những giá trị p < minP thì có thể chạy simulate luôn.
- Với giá trị p > minP, thì tìm cách chuyển `p` -> `p'` với `p'` là giá trị nhỏ nhất >= minP mà sau thực hiện 1 số vòng thì p thành p`. Để xác định việc này ta nhìn sự thay đổi của x (x >= minP) sau mỗi vòng.

[View source E_NoiVongTayLon (Java)](src/E_NoiVongTayLon.java)


### F. Tách xâu
Bài này cần tìm cách tách sâu `s` thành các từ cho trước trong từ điển.
Để làm việc này ta sử dụng quy hoạch động cộng với lưu vết.

- `bool[] isOK`, với isOK[i] là khả năng để xâu s[0...i] (bao gồm cả i) có thể tách thành các từ trong từ điển. Kết quả cần tìm là isOK[l-1] với l là độ dài xâu s.
- `int[] start`, xâu s bắt đầu từ `start[i]` tới vị trí `i` (bao gồm cả i) có trong từ điển, và `isOK[i-1] = true`

[View source F_TachXau (Java)](src/F_TachXau.java)

[H_SapXepKhoHang.java](..%2Falgo%2Fi_vcc23%2FH_SXKH.java)
### G. Phủ sóng internet
Bài này trước cần giải bài toán 1D sẽ dễ hình dung, sau đó bài toán 2D chỉ đơn giản là giải 2 bài 1D và lấy tích nhân với nhau.
TODO: more details

[View source G_PhuSongInternet](src/G_PhuSongInternet.java)


##
### I. Hỗ trợ khách hàng


### J. Rô bôt thông minh

