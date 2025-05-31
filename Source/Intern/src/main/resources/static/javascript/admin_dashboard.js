document.addEventListener("DOMContentLoaded", () => {
    const deleteBtns = document.querySelectorAll('.delete-btn');

    deleteBtns.forEach((btn) => {
        btn.addEventListener('click', (e) => {
            e.preventDefault(); // Ngừng hành động mặc định của form

            // Lấy thông tin tên người dùng từ data-username
            const userName = btn.getAttribute('data-username');

            if (confirm(`Are you sure you want to delete the user: ${userName}?`)) {
                const form = btn.closest('form'); // Lấy form chứa nút xóa

                // Gửi yêu cầu DELETE đến server
                fetch(form.action, {
                    method: 'POST', // POST method vì chúng ta đang sử dụng method override
                    body: new URLSearchParams(new FormData(form)),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then(response => {
                    if (response.ok) {
                        alert("User deleted successfully!");
                        window.location.reload(); // Reload lại trang để cập nhật dữ liệu
                    } else {
                        alert("Failed to delete user.");
                    }
                }).catch(error => {
                    console.error("Error deleting user:", error);
                    alert("An error occurred while deleting the user.");
                });
            }
        });
    });
});
