// ---------------------- LOGIN ----------------------
document.getElementById("loginForm").addEventListener("submit", function(e) {
  e.preventDefault();
  const username = document.getElementById("username").value.trim();
  const password = document.getElementById("password").value.trim();

  if (username === "teacher" && password === "1234") {
    document.getElementById("login").classList.remove("active");
    document.getElementById("home").classList.add("active");
    document.getElementById("navBar").style.display = "block";
  } else {
    alert("Invalid username or password");
  }
});

// ---------------------- STORAGE SETUP ----------------------
let students = JSON.parse(localStorage.getItem("students")) || [
  { id: 1, name: "Aarav" }, { id: 2, name: "Priya" }, { id: 3, name: "Rahul" },
  { id: 4, name: "Sneha" }, { id: 5, name: "Ananya" }, { id: 6, name: "Vikram" },
  { id: 7, name: "Karthik" }, { id: 8, name: "Ishita" }, { id: 9, name: "Rohan" },
  { id: 10, name: "Diya" }, { id: 11, name: "Siddharth" }, { id: 12, name: "Meera" },
  { id: 13, name: "Aditya" }, { id: 14, name: "Pooja" }, { id: 15, name: "Lakshmi" },
  { id: 16, name: "Manoj" }, { id: 17, name: "Divya" }, { id: 18, name: "Gopal" },
  { id: 19, name: "Nisha" }, { id: 20, name: "Kiran" }, { id: 21, name: "Hari" },
  { id: 22, name: "Preethi" }, { id: 23, name: "Suresh" }, { id: 24, name: "Deepa" },
  { id: 25, name: "Ravi" }
];

let attendance = JSON.parse(localStorage.getItem("attendance")) || {};

function saveData() {
  localStorage.setItem("students", JSON.stringify(students));
  localStorage.setItem("attendance", JSON.stringify(attendance));
}

// ---------------------- NAVIGATION ----------------------
function showSection(id) {
  document.querySelectorAll("section").forEach(s => s.classList.remove("active"));
  document.getElementById(id).classList.add("active");
  if (id === "students") renderStudents();
  if (id === "attendance") renderAttendance();
  if (id === "summary") renderSummary();
}

// ---------------------- STUDENT ADD / DELETE ----------------------
document.getElementById("studentForm").addEventListener("submit", function(e) {
  e.preventDefault();
  const name = document.getElementById("studentName").value.trim();
  if (name) {
    const id = Date.now();
    students.push({ id, name });
    attendance[id] = { present: 0, absent: 0 };
    saveData();
    renderStudents();
    document.getElementById("studentName").value = "";
  }
});

function renderStudents() {
  const list = document.getElementById("studentList");
  list.innerHTML = "";
  students.forEach(s => {
    const li = document.createElement("li");
    li.innerHTML = `
      ${s.name}
      <button onclick="deleteStudent(${s.id})">🗑 Delete</button>
    `;
    list.appendChild(li);
  });
}

function deleteStudent(id) {
  students = students.filter(s => s.id !== id);
  delete attendance[id];
  saveData();
  renderStudents();
}

// ---------------------- ATTENDANCE ----------------------
function renderAttendance() {
  const area = document.getElementById("attendanceArea");
  area.innerHTML = "";
  students.forEach(s => {
    const div = document.createElement("div");
    div.classList.add("student-mark");
    div.innerHTML = `
      <strong>${s.name}</strong><br>
      <button onclick="markAttendance(${s.id}, true)">✅ Present</button>
      <button onclick="markAttendance(${s.id}, false)">❌ Absent</button>
    `;
    area.appendChild(div);
  });
}

function markAttendance(id, present) {
  id = Number(id);
  if (!attendance[id]) attendance[id] = { present: 0, absent: 0 };
  if (present) attendance[id].present++;
  else attendance[id].absent++;
  saveData();

  const student = students.find(s => s.id === id);
  if (student) alert("Attendance marked for ${student.name}");
  renderSummary();
}

// ---------------------- SUMMARY ----------------------
function renderSummary() {
  const tbody = document.querySelector("#summaryTable tbody");
  tbody.innerHTML = "";
  students.forEach(s => {
    const att = attendance[s.id] || { present: 0, absent: 0 };
    const total = att.present + att.absent;
    const percent = total > 0 ? ((att.present / total) * 100).toFixed(2) : 0;
    tbody.innerHTML += `
      <tr>
        <td>${s.name}</td>
        <td>${att.present}</td>
        <td>${att.absent}</td>
        <td>${percent}%</td>
      </tr>
    `;
  });
}

