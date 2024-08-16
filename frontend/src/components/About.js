import React from "react";
import "./About.css";

function About() {
  return (
    <div className="about_container">
      <div className="about_info">
        <ul>
          <li className="student">
            <div className="student_pic">
              <img
                src={require("../images/DeidraPic.png")}
                alt="Deidra"
                width="200px"
                height="200px"
              />
            </div>
            <h2 className="student_name">Deidra Zamonski-Blake</h2>
            <p className="bio">
              Deidra is an entry-level full-stack web developer located in
              Columbus, OH. She and her husband of three years, Christopher, had
              their first child, Beorn, at the end of 2023 and she will soon be
              a new graduate of the trade school We Can Code It.
            </p>
          </li>
          <li className="student">
            <div className="student_pic">
              <img
                src={require("../images/RossPic.png")}
                className="student-pic"
                alt="Ross"
                width="200px"
                height="200px"
              ></img>
            </div>
            <h2 className="student_name">Ross McDonald</h2>
            <p className="bio">
              Ross is a We Can Code It student with a background in political
              science. He graduated from OSU with a bachelors in political
              science. Since then he's aspired to become a video game developer.
              He's currently honing my skills in backend and frontend
              programming languages.
            </p>
          </li>
          <li className="student">
            <div className="student_pic">
              <img
                src={require("../images/KatePic.jpg")}
                alt="Kate"
                width="200px"
                height="200px"
              />
            </div>
            <h2 className="student_name">Kate Locke</h2>
            <p className="bio">
              Kate is a full-stack development student at We Can Code it with a
              background in communication studies. In 2017 she graduated from
              Kent State University and has since found an interest in front-end
              development. She is set to graduate from WCCI in August of 2024.
            </p>
          </li>
        </ul>
      </div>
    </div>
  );
}

export default About;
